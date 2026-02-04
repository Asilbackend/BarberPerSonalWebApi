package uz.anvarovich.barber_personal_website_api.services.domain.notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.anvarovich.barber_personal_website_api.entity.Notification;
import uz.anvarovich.barber_personal_website_api.entity.user.User;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationService {
    void notifyAdmin(String message, List<User> admins);

    Page<Notification> findAllByUserId(Long currentUserId, Pageable pageable);

    Notification findById(Long notificationId);

    void setReadAt(LocalDateTime now, Notification notification);
}
