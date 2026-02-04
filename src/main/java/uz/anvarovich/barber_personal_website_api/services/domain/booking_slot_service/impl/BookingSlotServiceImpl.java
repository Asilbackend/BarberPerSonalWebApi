package uz.anvarovich.barber_personal_website_api.services.domain.booking_slot_service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.anvarovich.barber_personal_website_api.entity.BookingSlot;
import uz.anvarovich.barber_personal_website_api.entity.booking.Booking;
import uz.anvarovich.barber_personal_website_api.entity.time_slot.TimeSlot;
import uz.anvarovich.barber_personal_website_api.repository.BookingSlotRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingSlotServiceImpl implements BookingSlotService {

    private final BookingSlotRepository bookingSlotRepository;

    @Override
    public void book(Booking booking, List<TimeSlot> timeSlots) {
        timeSlots = timeSlots.stream()
                .sorted(Comparator.comparing(TimeSlot::getId))
                .toList();
        List<BookingSlot> bookingSlots = new ArrayList<>();
        for (int i = 0; i < timeSlots.size(); i++) {
            TimeSlot timeSlot = timeSlots.get(i);
            bookingSlots.add(new BookingSlot(booking, timeSlot, i + 1));
        }
        bookingSlotRepository.saveAll(bookingSlots);
    }
}
