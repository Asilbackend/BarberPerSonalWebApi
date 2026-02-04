package uz.anvarovich.barber_personal_website_api.dto.resp_dto;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record BookRespDto(
        Long bookingId,
        LocalDate date,
        List<TimeSlotDto> slotTimes
) {
    public record TimeSlotDto(
            LocalTime slotTime,
            Boolean isOutsideSchedule) {
    }
}
