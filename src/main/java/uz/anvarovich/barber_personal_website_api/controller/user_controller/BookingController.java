package uz.anvarovich.barber_personal_website_api.controller.user_controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.BookDto;
import uz.anvarovich.barber_personal_website_api.dto.resp_dto.BookRespDto;
import uz.anvarovich.barber_personal_website_api.services.app.booking_service.BookingServiceApp;

import java.util.List;

@Tag(
        name = "User - Book qilish, cancel qilish, booklarni get qilish "
)
@RestController
@RequestMapping("/api/user/book")
@RequiredArgsConstructor
public class BookingController {
    private final BookingServiceApp bookingServiceApp;

    @PostMapping
    public HttpEntity<?> bookByUser(@RequestBody BookDto bookDto) {
        bookingServiceApp.bookByUser(bookDto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public HttpEntity<?> cancelByUser(@RequestParam Long bookingId) {
        bookingServiceApp.cancelByUser(bookingId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public HttpEntity<List<BookRespDto>> getMyBooks() {
        List<BookRespDto> myBooks = bookingServiceApp.getMyBooks();
        return ResponseEntity.ok(myBooks);
    }
}
