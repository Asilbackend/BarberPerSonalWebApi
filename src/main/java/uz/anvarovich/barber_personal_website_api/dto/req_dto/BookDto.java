package uz.anvarovich.barber_personal_website_api.dto.req_dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public record BookDto(
        LocalDate date,
        List<Long> timeSlotsId
) implements Serializable {
}
