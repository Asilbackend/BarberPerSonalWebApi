package uz.anvarovich.barber_personal_website_api.dto.req_dto;

import java.time.LocalTime;

public record SystemSettingDto(
        /*@Positive*/
        Integer visibleDaysForUsers,
        LocalTime defaultWorkStartTime,
        LocalTime defaultWorkEndTime,
        Integer defaultSlotDurationMin
) {
}
