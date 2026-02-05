package uz.anvarovich.barber_personal_website_api.services.app.notification.impl;

import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.anvarovich.barber_personal_website_api.entity.Notification;
import uz.anvarovich.barber_personal_website_api.services.app.notification.NotificationAppService;
import uz.anvarovich.barber_personal_website_api.services.domain.notification.NotificationService;
import uz.anvarovich.barber_personal_website_api.services.domain.user_service.UserService;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class NotificationAppServiceImpl implements NotificationAppService {
    private final UserService userService;
    private final NotificationService notificationService;

    @Override
    public Page<Notification> getAllByCurrentUser(Pageable pageable) {
        Long currentUserId = userService.getCurrentUserId();
        return notificationService.findAllByUserId(currentUserId, pageable);
    }

    @Override
    @Transactional
    public void read(Long notificationId) {
        Long currentUserId = userService.getCurrentUserId();
        Notification notification = notificationService.findById(notificationId);
        if (!notification.getToUser().getId().equals(currentUserId)) {
            throw new IllegalArgumentException("Bu xabar sizga tegishli emas");
        }
        notificationService.setReadAt(LocalDateTime.now(), notification);
    }
}
