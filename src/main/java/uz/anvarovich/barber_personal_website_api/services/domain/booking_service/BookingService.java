package uz.anvarovich.barber_personal_website_api.services.domain.booking_service;

import uz.anvarovich.barber_personal_website_api.entity.booking.Booking;

import java.time.LocalDate;
import java.util.List;

public interface BookingService {
    Booking createBooking(LocalDate date, int size);

    List<Booking> findAllCurrentUser();

    List<Booking> findAllByDate(LocalDate date);

    Booking findById(Long bookingId);

    void cancel(Booking booking);
}
