package uz.anvarovich.barber_personal_website_api.dto.resp_dto;

import java.time.LocalDateTime;

public record NotificationRespDto(
        Long notificationId,
        String message,
        LocalDateTime sendAt,
        LocalDateTime readAt
) {
}
