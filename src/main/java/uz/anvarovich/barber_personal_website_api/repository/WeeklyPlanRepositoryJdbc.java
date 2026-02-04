package uz.anvarovich.barber_personal_website_api.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.anvarovich.barber_personal_website_api.dto.resp_dto.WeeklyPlanRespDto;
import uz.anvarovich.barber_personal_website_api.entity.time_slot.SlotStatus;
import uz.anvarovich.barber_personal_website_api.projection.TimeSlotProjection;
import uz.anvarovich.barber_personal_website_api.validator.WeeklyPlanValidator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class WeeklyPlanRepositoryJdbc {
    private final JdbcTemplate jdbcTemplate;

    public WeeklyPlanRespDto getWeeklyPlan(LocalDate weekStartDate, int plusDays, boolean admin) {
        WeeklyPlanValidator.validateStartDate(weekStartDate);
        LocalDate weekEndDate = weekStartDate.plusDays(plusDays);

        String sql = """
                SELECT 
                    dp.date as date, 
                    dp.is_day_off as dayOff, 
                    ab.reason as dayOffReason,
                    ts.id as timeSlotId, 
                    ts.start_time as startTime, 
                    ts.end_time as endTime, 
                    ts.slot_status as slotStatus, 
                    ts.is_outside_schedule as isOutsideSchedule
                FROM daily_plan dp
                JOIN weekly_plan wp ON dp.weekly_plan_id = wp.id
                LEFT JOIN admin_block ab ON ab.daily_plan_id = dp.id
                LEFT JOIN time_slot ts ON ts.daily_plan_id = dp.id
                WHERE wp.week_start_date = ? 
                AND dp.date BETWEEN ? AND ?
                ORDER BY dp.date ASC, ts.start_time ASC
                """;

        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql,
                weekStartDate, weekStartDate, weekEndDate);

        // Agar ma'lumot topilmasa, bo'sh ro'yxat qaytaramiz
        if (rows.isEmpty()) {
            return new WeeklyPlanRespDto(Collections.emptyList());
        }

        // Ma'lumotlarni Date bo'yicha guruhlaymiz
        Map<LocalDate, List<Map<String, Object>>> groupedByDate = rows.stream()
                .collect(Collectors.groupingBy(
                        row -> ((java.sql.Date) row.get("date")).toLocalDate(),
                        LinkedHashMap::new, // Tartibni saqlash uchun
                        Collectors.toList()
                ));

        List<WeeklyPlanRespDto.DayDto> days = new ArrayList<>();

        for (Map.Entry<LocalDate, List<Map<String, Object>>> entry : groupedByDate.entrySet()) {
            List<TimeSlotProjection> slots = new ArrayList<>();

            for (Map<String, Object> row : entry.getValue()) {
                // Agar time_slot_id null bo'lsa, demak bu kunda slot yo'q
                if (row.get("timeSlotId") != null) {
                    slots.add(new TimeSlotProjectionImpl(
                            (Long) row.get("timeSlotId"),
                            ((java.sql.Time) row.get("startTime")).toLocalTime(),
                            ((java.sql.Time) row.get("endTime")).toLocalTime(),
                            SlotStatus.valueOf((String) row.get("slotStatus")),
                            (Boolean) row.get("isOutsideSchedule")
                    ));
                }
            }

            // Birinchi qatordan kunlik ma'lumotlarni olamiz
            Map<String, Object> firstRow = entry.getValue().get(0);
            days.add(new WeeklyPlanRespDto.DayDto(
                    entry.getKey(),
                    (Boolean) firstRow.get("dayOff"),
                    admin ? (String) firstRow.get("dayOffReason") : null,
                    slots
            ));
        }

        return new WeeklyPlanRespDto(days);
    }


    private static class TimeSlotProjectionImpl implements TimeSlotProjection {
        private final Long timeSlotId;
        private final LocalTime startTime;
        private final LocalTime endTime;
        private final SlotStatus slotStatus;
        private final Boolean isOutsideSchedule;

        public TimeSlotProjectionImpl(Long timeSlotId, LocalTime startTime,
                                      LocalTime endTime, SlotStatus slotStatus,
                                      Boolean isOutsideSchedule) {
            this.timeSlotId = timeSlotId;
            this.startTime = startTime;
            this.endTime = endTime;
            this.slotStatus = slotStatus;
            this.isOutsideSchedule = isOutsideSchedule;
        }

        @Override
        public Long getTimeSlotId() {
            return timeSlotId;
        }

        @Override
        public LocalTime getStartTime() {
            return startTime;
        }

        @Override
        public LocalTime getEndTime() {
            return endTime;
        }

        @Override
        public SlotStatus getSlotStatus() {
            return slotStatus;
        }

        @Override
        public Boolean getIsOutsideSchedule() {
            return isOutsideSchedule;
        }
    }

}