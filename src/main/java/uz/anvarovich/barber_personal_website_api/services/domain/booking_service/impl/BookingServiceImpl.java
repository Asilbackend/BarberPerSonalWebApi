package uz.anvarovich.barber_personal_website_api.services.domain.booking_service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.anvarovich.barber_personal_website_api.entity.booking.Booking;
import uz.anvarovich.barber_personal_website_api.entity.enums.BookingStatus;
import uz.anvarovich.barber_personal_website_api.entity.user.User;
import uz.anvarovich.barber_personal_website_api.handler.exceptions.AlreadyExist;
import uz.anvarovich.barber_personal_website_api.repository.BookingRepository;
import uz.anvarovich.barber_personal_website_api.services.domain.booking_service.BookingService;
import uz.anvarovich.barber_personal_website_api.services.domain.user_service.UserService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final UserService userService;
    private final BookingRepository bookingRepository;

    @Override
    @Transactional
    public Booking createBooking(LocalDate date, int size) {
        User user = userService.getCurrentUser();
        Optional<Booking> booking = bookingRepository.findByDateAndUserId(date, user.getId());
        if (booking.isEmpty()) {
            return bookingRepository.save(new Booking(user, date, size, BookingStatus.ACTIVE));
        } else {
            Booking bookingCurrent = booking.get();
            if (bookingCurrent.getStatus().equals(BookingStatus.ACTIVE)) {
                throw new AlreadyExist("Bu kun uchun allaqachon BOOK qilgansiz");
            } else if (bookingCurrent.getStatus().equals(BookingStatus.CANCELLED)) {
                bookingCurrent.setStatus(BookingStatus.ACTIVE);
                return bookingRepository.save(bookingCurrent);
            }
        }
        throw new RuntimeException("noma'lum xatolik ketti create Booking");
    }

    @Override
    public List<Booking> findAllCurrentUser() {
        return bookingRepository.findByUserId(userService.getCurrentUserId());
    }

    @Override
    public List<Booking> findAllByDate(LocalDate date) {
        return bookingRepository.findAllByDate(date);
    }

    @Override
    public Booking findById(Long bookingId) {
        return bookingRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("Booking not found"));
    }

    @Override
    public void cancel(Booking booking) {
        booking.setStatus(BookingStatus.CANCELLED);
        bookingRepository.save(booking);
    }
}

