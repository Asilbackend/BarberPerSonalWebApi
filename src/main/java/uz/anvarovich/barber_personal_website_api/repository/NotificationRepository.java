package uz.anvarovich.barber_personal_website_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import uz.anvarovich.barber_personal_website_api.entity.Notification;
import uz.anvarovich.barber_personal_website_api.entity.user.User;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    Page<Notification> findAllByToUserId(Long currentUserId, Pageable pageable);
}