package uz.anvarovich.barber_personal_website_api.services.app.admin_block.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.anvarovich.barber_personal_website_api.dto.AdminBlockDto;
import uz.anvarovich.barber_personal_website_api.entity.DailyPlan;
import uz.anvarovich.barber_personal_website_api.entity.abs.BaseEntity;
import uz.anvarovich.barber_personal_website_api.entity.booking.Booking;
import uz.anvarovich.barber_personal_website_api.entity.time_slot.TimeSlot;
import uz.anvarovich.barber_personal_website_api.entity.user.User;
import uz.anvarovich.barber_personal_website_api.services.app.admin_block.AdminBlockCServiceApp;
import uz.anvarovich.barber_personal_website_api.services.domain.admin_block.AdminBlockCService;
import uz.anvarovich.barber_personal_website_api.services.domain.booking_service.BookingService;
import uz.anvarovich.barber_personal_website_api.services.domain.booking_slot_service.BookingSlotService;
import uz.anvarovich.barber_personal_website_api.services.domain.dailyPlan_service.DailyPlanService;
import uz.anvarovich.barber_personal_website_api.services.domain.notification.NotificationService;
import uz.anvarovich.barber_personal_website_api.services.domain.time_slote.TimeSlotService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminBlockCServiceAppImpl implements AdminBlockCServiceApp {
    private final AdminBlockCService adminBlockCService;
    private final DailyPlanService dailyPlanService;
    private final TimeSlotService timeSlotService;
    private final BookingSlotService bookingSlotService;
    private final BookingService bookingService;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public void addAdditionalBlock(AdminBlockDto adminBlockDto) {
        DailyPlan byDate = dailyPlanService.findByDate(adminBlockDto.date());
        boolean success = dailyPlanService.setDayOff(byDate);
        if (!success) return;
        adminBlockCService.create(adminBlockDto, byDate);
        forceCancelUserBook(byDate.getDate());
        timeSlotService.blockByDailyPlanId(byDate.getId());
    }

    private void forceCancelUserBook(LocalDate date) {
        List<Booking> bookings = bookingService.findAllByDate(date);
        List<User> notifyUsers = new ArrayList<>();
        for (Booking booking : bookings) {
            bookingService.cancel(booking);
            bookingSlotService.deleteByBookingId(booking.getId());
            notifyUsers.add(booking.getUser());
        }
        String formattedDate = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        notificationService.notifyUsersAsync(
                """ 
                         Hurmatli mijoz!
                         %s kuni admin tomonidan kun band deb belgilandi.
                         Sizning zakazingiz bekor qilindi. Noqulaylik uchun uzr!
                         Yangi vaqt tanlash uchun appdan foydalaning.
                         Rahmat sabringiz uchun ☺
                        """.formatted(formattedDate),
                notifyUsers
        );
    }

    @Override
    public void cancelBlock(LocalDate date) {
        DailyPlan dailyPlan = dailyPlanService.findByDate(date);
        boolean success = dailyPlanService.cancelDayOff(dailyPlan);
        if (!success) {
            return;
        }
        adminBlockCService.deleteByDailyPlanId(dailyPlan.getId());
        timeSlotService.cancelBlockByDailyPlanId(dailyPlan.getId());
    }
}
