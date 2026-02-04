package uz.anvarovich.barber_personal_website_api.validator;

import uz.anvarovich.barber_personal_website_api.dto.req_dto.DailyPlanDto;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class WeeklyPlanValidator {

    private WeeklyPlanValidator() {
        // utility class - instantiation taqiqlanadi
    }

    private static void validateWeekDates(LocalDate start, LocalDate end) {
        if (start == null) {
            throw new IllegalArgumentException("weekStartDate is required");
        }
        if (end == null) {
            throw new IllegalArgumentException("weekEndDate is required");
        }

        if (end.isBefore(start)) {
            throw new IllegalArgumentException("weekEndDate must be after or equal to weekStartDate");
        }

        if (end.isAfter(start.plusDays(6))) {
            throw new IllegalArgumentException("The period cannot be longer than 7 days");
        }

        if (end.isBefore(start.plusDays(6))) {
            throw new IllegalArgumentException("The period must be exactly 7 days for a weekly plan");
        }

        // Odatda haftalik plan Monday'dan boshlanadi
        if (start.getDayOfWeek() != DayOfWeek.MONDAY) {
            throw new IllegalArgumentException("weekStartDate must be a Monday");
        }
    }

    private static void validateDailyPlan(DailyPlanDto daily, LocalDate weekStart, LocalDate weekEnd) {
        if (daily == null) {
            throw new IllegalArgumentException("DailyPlanDto cannot be null");
        }

        if (daily.date() == null) {
            throw new IllegalArgumentException("date is required in daily plan");
        }

        // Sana hafta oralig'ida bo'lishi kerak
        if (daily.date().isBefore(weekStart) || daily.date().isAfter(weekEnd)) {
            throw new IllegalArgumentException("Daily plan date is outside the week range: " + daily.date());
        }

        if (daily.workStartTime() == null) {
            throw new IllegalArgumentException("workStartTime is required");
        }

        if (daily.workEndTime() == null) {
            throw new IllegalArgumentException("workEndTime is required");
        }

        if (daily.workEndTime().isBefore(daily.workStartTime())) {
            throw new IllegalArgumentException("workEndTime must be after workStartTime");
        }

        if (daily.workEndTime().equals(daily.workStartTime())) {
            throw new IllegalArgumentException("workStartTime and workEndTime cannot be equal");
        }

        if (daily.slotDurationMin() == null || daily.slotDurationMin() <= 0) {
            throw new IllegalArgumentException("slotDurationMin must be positive");
        }

        if (daily.slotDurationMin() < 5) {
            throw new IllegalArgumentException("slotDurationMin should be at least 5 minutes");
        }

        // slotsStartTime ni tekshirish
        validateSlots(daily.workStartTime(), daily.workEndTime(), daily.slotDurationMin(), daily.slotsStartTime());
    }

    private static void validateSlots(LocalTime start, LocalTime end, Integer slotDurationMin, List<LocalTime> slots) {
        if (slots == null) {
            throw new IllegalArgumentException("slotsStartTime cannot be null");
        }

        if (slots.isEmpty()) {
            // Agar bo'sh bo'lishiga ruxsat bersangiz, bu qatorni o'chirib tashlang
            throw new IllegalArgumentException("slotsStartTime cannot be empty");
        }

        Duration slotDuration = Duration.ofMinutes(slotDurationMin);

        for (int i = 0; i < slots.size(); i++) {
            LocalTime slot = slots.get(i);

            if (slot == null) {
                throw new IllegalArgumentException("slot time cannot be null at index " + i);
            }

            // Ish vaqti ichida bo'lishi kerak
            if (slot.isBefore(start) || slot.isAfter(end)) {
                throw new IllegalArgumentException("Slot time is outside working hours: " + slot);
            }

            // Keyingi slot bilan qoplashmasligi kerak (agar kerak bo'lsa)
            if (i > 0) {
                LocalTime prev = slots.get(i - 1);
                if (!slot.equals(prev.plus(slotDuration))) {
                    // Agar slotlar ketma-ket bo'lishi shart bo'lmasa, bu qatorni olib tashlang
                    throw new IllegalArgumentException("Slots must be consecutive. Gap between " + prev + " and " + slot);
                }
            }
        }

        // Oxirgi slot + duration end time'dan oshib ketmasligi kerak
        LocalTime lastSlot = slots.get(slots.size() - 1);
        LocalTime lastSlotEnd = lastSlot.plus(slotDuration);
        if (lastSlotEnd.isAfter(end)) {
            throw new IllegalArgumentException("Last slot ends after workEndTime: " + lastSlotEnd);
        }
    }

    private static void validateAllWeekDaysArePresent(LocalDate weekStart, List<DailyPlanDto> dailyPlans) {
        Set<LocalDate> providedDates = new HashSet<>();
        for (DailyPlanDto d : dailyPlans) {
            providedDates.add(d.date());
        }

        for (int i = 0; i < 7; i++) {
            LocalDate expected = weekStart.plusDays(i);
            if (!providedDates.contains(expected)) {
                throw new IllegalArgumentException("Missing daily plan for date: " + expected);
            }
        }
    }

    public static void validateStartDate(LocalDate weekStartDate) {
        DayOfWeek dayOfWeek = weekStartDate.getDayOfWeek();
        DayOfWeek monday = DayOfWeek.MONDAY;
        if (!dayOfWeek.equals(monday)) {
            throw new RuntimeException("siz kiritgan sana Dushanba emas!!");
        }
    }
}
