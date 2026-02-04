package uz.anvarovich.barber_personal_website_api.services.domain.notification.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.anvarovich.barber_personal_website_api.entity.Notification;
import uz.anvarovich.barber_personal_website_api.entity.user.User;
import uz.anvarovich.barber_personal_website_api.repository.NotificationRepository;
import uz.anvarovich.barber_personal_website_api.services.domain.notification.NotificationService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;


    @Override
    public void notifyAdmin(String message, List<User> admins) {
        List<Notification> unsaved = new ArrayList<>();
        for (User admin : admins) {
            Notification notification = new Notification(admin, message, LocalDateTime.now(), null);
            unsaved.add(notification);
        }
        notificationRepository.saveAll(unsaved);
    }

    @Override
    public Page<Notification> findAllByUserId(Long currentUserId, Pageable pageable) {
        return notificationRepository.findAllByToUserId(currentUserId, pageable);
    }

    @Override
    public Notification findById(Long notificationId) {
        return notificationRepository.findById(notificationId).orElseThrow(() -> new EntityNotFoundException("notification is not found"));
    }

    @Override
    @Transactional
    public void setReadAt(LocalDateTime now, Notification notification) {
        notification.setReadAt(now);
        notificationRepository.save(notification);
    }
}
