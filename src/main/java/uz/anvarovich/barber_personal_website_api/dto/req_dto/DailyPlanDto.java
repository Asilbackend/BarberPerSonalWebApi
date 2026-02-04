package uz.anvarovich.barber_personal_website_api.dto.req_dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public record DailyPlanDto(
        LocalDate date,
        LocalTime workStartTime,
        LocalTime workEndTime,
        Integer slotDurationMin,
        List<LocalTime> slotsStartTime
) implements Serializable {
}
