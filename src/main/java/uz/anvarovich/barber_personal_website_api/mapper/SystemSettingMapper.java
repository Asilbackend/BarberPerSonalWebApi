package uz.anvarovich.barber_personal_website_api.mapper;

import uz.anvarovich.barber_personal_website_api.dto.req_dto.SystemSettingDto;
import uz.anvarovich.barber_personal_website_api.entity.SystemSettings;

public final class SystemSettingMapper {
    private SystemSettingMapper(){

    }
    public static SystemSettings toEntity(SystemSettingDto dto) {
        SystemSettings systemSettings = new SystemSettings();
        systemSettings.setDefaultSlotDurationMin(dto.defaultSlotDurationMin());
        systemSettings.setDefaultWorkStartTime(dto.defaultWorkStartTime());
        systemSettings.setDefaultWorkEndTime(dto.defaultWorkEndTime());
        systemSettings.setVisibleDaysForUsers(dto.visibleDaysForUsers());
        return systemSettings;
    }

    public static SystemSettingDto toDto(SystemSettings entity) {
        return new SystemSettingDto(
                entity.getVisibleDaysForUsers(),
                entity.getDefaultWorkStartTime(),
                entity.getDefaultWorkEndTime(),
                entity.getDefaultSlotDurationMin()
        );
    }
}
