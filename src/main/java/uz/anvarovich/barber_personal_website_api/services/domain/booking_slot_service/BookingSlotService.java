package uz.anvarovich.barber_personal_website_api.services.domain.booking_slot_service;

import org.springframework.data.jpa.repository.Query;
import uz.anvarovich.barber_personal_website_api.entity.booking.Booking;
import uz.anvarovich.barber_personal_website_api.entity.time_slot.TimeSlot;

import java.util.List;

public interface BookingSlotService {
    void book(Booking booking, List<TimeSlot> timeSlots);


    List<TimeSlot> findTimeSlotsByBookingId(Long id);
}
