package uz.anvarovich.barber_personal_website_api.services.app.notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import uz.anvarovich.barber_personal_website_api.entity.Notification;

public interface NotificationAppService {
    Page<Notification> getAllByCurrentUser(Pageable pageable);

    void read(Long notificationId);

}
