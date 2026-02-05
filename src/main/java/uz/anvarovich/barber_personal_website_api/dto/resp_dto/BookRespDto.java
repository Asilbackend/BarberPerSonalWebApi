package uz.anvarovich.barber_personal_website_api.dto.resp_dto;

import uz.anvarovich.barber_personal_website_api.entity.enums.BookingStatus;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record BookRespDto(
        Long bookingId,
        LocalDate date,
        List<TimeSlotDto> slotTimes,
        BookingStatus bookingStatus
) {
    public record TimeSlotDto(
            LocalTime slotTime,
            Boolean isOutsideSchedule) {
    }
}
