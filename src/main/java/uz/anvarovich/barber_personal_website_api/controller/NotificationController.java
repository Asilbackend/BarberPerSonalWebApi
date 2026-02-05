package uz.anvarovich.barber_personal_website_api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.anvarovich.barber_personal_website_api.dto.resp_dto.NotificationRespDto;
import uz.anvarovich.barber_personal_website_api.entity.Notification;
import uz.anvarovich.barber_personal_website_api.services.app.notification.NotificationAppService;

@Tag(
        name = "Notification -  get qilish"
)
@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationAppService notificationAppService;

    @GetMapping
    public ResponseEntity<Page<NotificationRespDto>> getNotifications(Pageable pageable) {
        Page<Notification> notificationsPage = notificationAppService.getAllByCurrentUser(pageable);
        Page<NotificationRespDto> dtoPage = notificationsPage.map(notification ->
                new NotificationRespDto(
                        notification.getId(),
                        notification.getMessage(),
                        notification.getSendAt(),
                        notification.getReadAt()
                )
        );
        return ResponseEntity.ok(dtoPage);
    }

    @PostMapping
    public ResponseEntity<?> readNotification(@RequestParam Long notificationId) {
        notificationAppService.read(notificationId);
        return ResponseEntity.notFound().build();
    }
}
