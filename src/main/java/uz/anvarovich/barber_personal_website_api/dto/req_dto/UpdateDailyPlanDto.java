package uz.anvarovich.barber_personal_website_api.dto.req_dto;

import java.io.Serializable;
import java.time.LocalTime;

public record UpdateDailyPlanDto(
        LocalTime workStartTime,
        LocalTime workEndTime,
        Integer slotDurationMin//30 min
) implements Serializable {
}
