package uz.anvarovich.barber_personal_website_api.dto.req_dto;

import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;

public record LoginRequestDTO(
        @NotBlank(message = "login is mandatory")  // Email bo'sh bo'lmasligi kerak
        String login,
        @NotBlank(message = "login is mandatory")
        String password
) implements Serializable {
}