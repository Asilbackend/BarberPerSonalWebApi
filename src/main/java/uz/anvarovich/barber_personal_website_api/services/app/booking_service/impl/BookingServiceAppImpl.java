package uz.anvarovich.barber_personal_website_api.services.app.booking_service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.BookDto;
import uz.anvarovich.barber_personal_website_api.dto.resp_dto.BookRespDto;
import uz.anvarovich.barber_personal_website_api.entity.enums.Booking;
import uz.anvarovich.barber_personal_website_api.entity.time_slot.TimeSlot;
import uz.anvarovich.barber_personal_website_api.services.app.booking_service.BookingServiceApp;
import uz.anvarovich.barber_personal_website_api.services.domain.booking_service.BookingService;
import uz.anvarovich.barber_personal_website_api.services.domain.booking_slot_service.BookingSlotService;
import uz.anvarovich.barber_personal_website_api.services.domain.time_slote.TimeSlotService;

import java.util.ArrayList;
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
    @Transactional
    public void blockChosenTimeSlotsByAdmin(BookDto bookDto) {
        bookSlots(bookDto, true);
    }

    @Override
    public List<BookRespDto> getMyBooks() {
        List<BookRespDto> bookRespList = new ArrayList<>();
        List<Booking> bookings = bookingService.findAllCurrentUser();
        for (Booking booking : bookings) {
            List<TimeSlot> timeSlots = bookingSlotService.findTimeSlotsByBookingId(booking.getId());
            List<BookRespDto.TimeSlotDto> timeSlotDtos = getTimeSlotDtos(timeSlots);
            bookRespList.add(new BookRespDto(booking.getId(), booking.getDate(), timeSlotDtos));
        }
        return bookRespList;
    }

    @Override
    @Transactional
    public void cancelByUser(Long bookingId) {
        bookingService.cancelById(bookingId);
        timeSlotService.cancelSlotsByUser(bookingSlotService.findTimeSlotsByBookingId(bookingId));
    }

    private static List<BookRespDto.TimeSlotDto> getTimeSlotDtos(List<TimeSlot> timeSlots) {
        return timeSlots.stream()
                .map(ts -> new BookRespDto.TimeSlotDto(
                        ts.getStartTime(),
                        ts.getIsOutsideSchedule()
                ))
                .toList();
    }

    @Override
    @Transactional
    public void bookByUser(BookDto bookDto) {
        List<TimeSlot> timeSlots = bookSlots(bookDto, false);
        Booking booking = bookingService.createBooking(bookDto.date(), timeSlots.size());
        bookingSlotService.book(booking, timeSlots);
    }

    @Override
    @Transactional
    public void cancelByAdmin(BookDto bookDto) {
        timeSlotService.cancelBookByAdmin(bookDto);
    }

}
