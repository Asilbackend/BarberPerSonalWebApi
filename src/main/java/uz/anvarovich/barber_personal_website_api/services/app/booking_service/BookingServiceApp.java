package uz.anvarovich.barber_personal_website_api.services.app.booking_service;

import uz.anvarovich.barber_personal_website_api.dto.req_dto.BookDto;
import uz.anvarovich.barber_personal_website_api.dto.resp_dto.BookRespDto;

import java.util.List;

public interface BookingServiceApp {

     void bookByUser(BookDto bookDto);

    void cancelByAdmin(BookDto bookDto);

    void blockChosenTimeSlotsByAdmin(BookDto bookDto);


    List<BookRespDto> getMyBooks();

    void cancelByUser(Long bookingId);
}
