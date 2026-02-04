package uz.anvarovich.barber_personal_website_api.dto.resp_dto;

import uz.anvarovich.barber_personal_website_api.projection.TimeSlotProjection;

import java.time.LocalDate;
import java.util.List;

public record WeeklyPlanRespDto(
        List<DayDto> days
) {
    public record DayDto(
            LocalDate date,
            Boolean dayOff,
            String dayOffReason, //User ga bu korinmaydi
            List<TimeSlotProjection> timeSlots
    ) {
    }
}