package uz.anvarovich.barber_personal_website_api.validator;

import uz.anvarovich.barber_personal_website_api.dto.req_dto.SystemSettingDto;

import java.util.Objects;

public class SystemSettingValidator {
    private SystemSettingValidator() {
    }

    public static void validate(SystemSettingDto dto) {
        Objects.requireNonNull(dto, "SystemSettingDto cannot be null");

        Objects.requireNonNull(dto.visibleDaysForUsers(), "visibleDaysForUsers is required");
        if (dto.visibleDaysForUsers() <= 0) {
            throw new IllegalArgumentException("visibleDaysForUsers must be > 0");
        }

        Objects.requireNonNull(dto.defaultWorkStartTime(), "defaultWorkStartTime is required");
        Objects.requireNonNull(dto.defaultWorkEndTime(), "defaultWorkEndTime is required");

        if (dto.defaultWorkEndTime().isBefore(dto.defaultWorkStartTime())) {
            throw new IllegalArgumentException("End time must be after start time");
        }

        Objects.requireNonNull(dto.defaultSlotDurationMin(), "defaultSlotDurationMin is required");
        if (dto.defaultSlotDurationMin() < 5) {
            throw new IllegalArgumentException("Slot duration must be ≥ 5 minutes");
        }
    }
}
