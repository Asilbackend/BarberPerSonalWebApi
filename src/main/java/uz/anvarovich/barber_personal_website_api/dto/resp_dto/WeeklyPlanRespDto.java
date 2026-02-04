package uz.anvarovich.barber_personal_website_api.dto.resp_dto;

import uz.anvarovich.barber_personal_website_api.projection.TimeSlotProjection;

import java.time.LocalDate;
import java.util.List;

public record UserWeeklyPlanRespDto(
        List<DayDto> days

) {
    private record DayDto(
            LocalDate date,
            Boolean dayOff,
            Boolean dayOffReason,
            List<TimeSlotProjection> timeSlotProjections
    ) {
    }
}