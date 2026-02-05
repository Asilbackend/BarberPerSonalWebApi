package uz.anvarovich.barber_personal_website_api.controller.admin_controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.BookDto;
import uz.anvarovich.barber_personal_website_api.services.app.booking_service.BookingServiceApp;

//qo'lda band qilish
@Tag(
        name = "Admin - Qo'lda band qilish",
        description = "Ma'lum kunga oid time slotlarni tanlab qolda band qiladi"
)
@RestController
@RequestMapping("/api/admin/book")
@RequiredArgsConstructor
public class AdminBookingController {
    private final BookingServiceApp bookingServiceApp;

    @Operation(
            summary = "Qo'lda band qilish",
            description = """
                    mavjud kun beriladi va shu kundagi timeSlotlar id si berilaadi.
                    ya'ni admin ma'lum ish kundagi bir nechta timeSlotlarni ozi qo'lda band qila oladi.
                    """
    )
    @PostMapping
    public HttpEntity<?> bookByAdmin(@RequestBody BookDto bookDto) {
        bookingServiceApp.blockChosenTimeSlotsByAdmin(bookDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Qo'lda band qilingan timeSlotlarni bekor qilish",
            description = """
                            
                    """
    )
    @DeleteMapping
    public HttpEntity<?> cancelBookByAdmin(@RequestBody BookDto bookDto) {
        bookingServiceApp.cancelByAdmin(bookDto);
        return ResponseEntity.noContent().build();
    }
}
