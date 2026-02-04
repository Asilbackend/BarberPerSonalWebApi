package uz.anvarovich.barber_personal_website_api.validator;

import uz.anvarovich.barber_personal_website_api.dto.req_dto.SystemSettingDto;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.UpdateDailyPlanDto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class DailyPlanValidator {
    private DailyPlanValidator() {

    }

    public static void validate(UpdateDailyPlanDto dto, LocalDate date) {
        if (!date.isAfter(LocalDate.now())) {
            throw new RuntimeException("ADMIN , siz ertangi kundan boshlab boshqa kunlarni update qila olasiz, bugunni emas");
        }

        Objects.requireNonNull(dto, "SystemSettingDto cannot be null");

        Objects.requireNonNull(dto.workStartTime(), "defaultWorkStartTime is required");
        Objects.requireNonNull(dto.workEndTime(), "defaultWorkEndTime is required");

        if (dto.workEndTime().isBefore(dto.workStartTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        Objects.requireNonNull(dto.slotDurationMin(), "defaultSlotDurationMin is required");
        if (dto.slotDurationMin() < 5) {
            throw new IllegalArgumentException("Slot duration must be ≥ 5 minutes");
        }
    }

    public static void validateDateIsVisible(LocalDate date, SystemSettingDto current) {
        if (LocalDate.now().plusDays(current.visibleDaysForUsers()).isBefore(date)) {
            throw new RuntimeException("siz bu kun haqida maulmot ololmaysiz, (unvisible days)");
        }
    }
}
