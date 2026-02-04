package uz.anvarovich.barber_personal_website_api.repository;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import uz.anvarovich.barber_personal_website_api.dto.resp_dto.WeeklyPlanRespDto;
import uz.anvarovich.barber_personal_website_api.entity.time_slot.SlotStatus;
import uz.anvarovich.barber_personal_website_api.projection.TimeSlotProjection;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class WeeklyPlanRepositoryClass {

    private final JdbcTemplate jdbcTemplate;

    public WeeklyPlanRespDto getWeeklyPlan(LocalDate weekStartDate) {
        LocalDate weekEndDate = weekStartDate.plusDays(6);
        String sql = """
            SELECT 
                all_dates.date as date,
                dp.date as daily_plan_date,
                dp.is_day_off,
                ts.id as time_slot_id,
                ts.start_time,
                ts.end_time,
                ts.slot_status,
                ts.is_outside_schedule
            FROM generate_series(?::date, ?::date, '1 day'::interval) AS all_dates(date)
            LEFT JOIN daily_plan dp ON dp.date = all_dates.date::date
            LEFT JOIN time_slot ts ON ts.daily_plan_id = dp.id
            ORDER BY all_dates.date, ts.start_time NULLS LAST
            """;
        // LinkedHashMap with correct types
        LinkedHashMap<LocalDate, DayDtoBuilder> dayBuilders = new LinkedHashMap<>();

        jdbcTemplate.query(sql,
                new Object[]{weekStartDate, weekEndDate},
                (ResultSet rs) -> {
                    LocalDate date = rs.getDate("date").toLocalDate();

                    // Get or create day builder
                    DayDtoBuilder dayBuilder = dayBuilders.computeIfAbsent(date,
                            DayDtoBuilder::new);

                    // Set day off status if daily plan exists
                    if (rs.getObject("daily_plan_date") != null && rs.getObject("is_day_off") != null) {
                        dayBuilder.setDayOff(rs.getBoolean("is_day_off"));
                    }

                    // Add time slot if exists
                    Long timeSlotId = rs.getObject("time_slot_id") != null
                            ? rs.getLong("time_slot_id")
                            : null;

                    if (timeSlotId != null) {
                        TimeSlotProjectionImpl projection = new TimeSlotProjectionImpl(
                                timeSlotId,
                                rs.getTime("start_time") != null
                                        ? rs.getTime("start_time").toLocalTime()
                                        : null,
                                rs.getTime("end_time") != null
                                        ? rs.getTime("end_time").toLocalTime()
                                        : null,
                                rs.getString("slot_status") != null
                                        ? SlotStatus.valueOf(rs.getString("slot_status"))
                                        : null,
                                rs.getObject("is_outside_schedule") != null
                                        ? rs.getBoolean("is_outside_schedule")
                                        : null
                        );
                        dayBuilder.addTimeSlot(projection);
                    }
                }
        );

        // Build final DTO
        List<WeeklyPlanRespDto.DayDto> days = dayBuilders.values().stream()
                .map(DayDtoBuilder::build)
                .collect(Collectors.toList());

        return new WeeklyPlanRespDto(days);
    }


    private static class DayDtoBuilder {
        private final LocalDate date;
        @Setter
        private Boolean dayOff = null;
        private final List<TimeSlotProjection> timeSlots = new ArrayList<>();

        public DayDtoBuilder(LocalDate date) {
            this.date = date;
        }

        public void addTimeSlot(TimeSlotProjection projection) {
            this.timeSlots.add(projection);
        }
       /* public void setDayOff(Boolean dayOff) {
            this.dayOff = dayOff;
        }*/

        public WeeklyPlanRespDto.DayDto build() {
            return new WeeklyPlanRespDto.DayDto(date, dayOff, null, timeSlots);
        }
    }

    // TimeSlotProjection implementation
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