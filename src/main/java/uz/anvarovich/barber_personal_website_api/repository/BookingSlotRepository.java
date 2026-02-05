package uz.anvarovich.barber_personal_website_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import uz.anvarovich.barber_personal_website_api.entity.BookingSlot;
import uz.anvarovich.barber_personal_website_api.entity.time_slot.TimeSlot;

import java.util.List;

public interface BookingSlotRepository extends JpaRepository<BookingSlot, Long> {
    @Query("select bs.timeSlot from BookingSlot bs where bs.booking.id= :bookingId")
    List<TimeSlot> findTimeSlotsByBookingId(Long bookingId);

    void deleteAllByBookingId(Long bookingId);
}