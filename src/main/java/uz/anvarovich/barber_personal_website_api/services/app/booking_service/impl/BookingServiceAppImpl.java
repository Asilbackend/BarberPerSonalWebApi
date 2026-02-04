package uz.anvarovich.barber_personal_website_api.services.app.booking_service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.BookDto;
import uz.anvarovich.barber_personal_website_api.entity.booking.Booking;
import uz.anvarovich.barber_personal_website_api.entity.time_slot.TimeSlot;
import uz.anvarovich.barber_personal_website_api.services.domain.booking_service.BookingService;
import uz.anvarovich.barber_personal_website_api.services.domain.booking_slot_service.BookingSlotService;
import uz.anvarovich.barber_personal_website_api.services.domain.time_slote.TimeSlotService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookingServiceAppImpl implements BookingServiceApp {
    private final TimeSlotService timeSlotService;
    private final BookingService bookingService;
    private final BookingSlotService bookingSlotService;

    private List<TimeSlot> bookSlots(BookDto dto, boolean isAdmin) {
        return timeSlotService.book(dto, isAdmin);
    }

    @Override
    public void bookByAdmin(BookDto bookDto) {
        bookSlots(bookDto, true);
    }

    @Override
    public void bookByUser(BookDto bookDto) {
        List<TimeSlot> timeSlots = bookSlots(bookDto, false);
        Booking booking = bookingService.createBooking(bookDto.date(), timeSlots.size());
        bookingSlotService.book(booking, timeSlots);
    }

    @Override
    public void cancelByAdmin(BookDto bookDto) {
        timeSlotService.cancelBookByAdmin(bookDto);
    }
}
