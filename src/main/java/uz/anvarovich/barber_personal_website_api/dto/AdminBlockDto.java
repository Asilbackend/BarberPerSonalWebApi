package uz.anvarovich.barber_personal_website_api.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

public record AdminBlockDto(
        @Schema(description = "Bloklanadigan sana (YYYY-MM-DD formatida)", example = "2026-03-10")
        LocalDate date,
        String reason
) {
}
