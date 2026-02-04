package uz.anvarovich.barber_personal_website_api.services.domain.booking_service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.anvarovich.barber_personal_website_api.entity.booking.Booking;
import uz.anvarovich.barber_personal_website_api.entity.booking.BookingStatus;
import uz.anvarovich.barber_personal_website_api.entity.user.User;
import uz.anvarovich.barber_personal_website_api.repository.BookingRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;


    @Override
    public Booking createBooking(LocalDate date, int size) {
        User user = new User();
        return bookingRepository.save(new Booking(user, date, size, BookingStatus.ACTIVE));
    }
}

