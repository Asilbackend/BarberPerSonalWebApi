package uz.anvarovich.barber_personal_website_api.controller.admin_controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.anvarovich.barber_personal_website_api.dto.AdminBlockDto;
import uz.anvarovich.barber_personal_website_api.services.app.admin_block.AdminBlockCServiceApp;
import uz.anvarovich.barber_personal_website_api.validator.AdminBlockValidator;

import java.time.LocalDate;

//qo'shimcha bandlik kiritish
@RestController
@RequestMapping("/api/admin/block")
@RequiredArgsConstructor
@Tag(
        name = "Admin - Qo'shimcha bandlik boshqarish",
        description = "Admin tomonidan ma'lum kunlarni bloklash va blokni bekor qilish operatsiyalari"
)
public class AdminBlockController {
    private final AdminBlockCServiceApp adminBlockCServiceApp;

    /* @ApiResponses(value = {
             @ApiResponse(
                     responseCode = "204",
                     description = "Kun muvaffaqiyatli bloklandi"
             ),
             @ApiResponse(
                     responseCode = "400",
                     description = "Noto'g'ri sana formati yoki sanada xato (masalan, o'tmishdagi sana)",
                     content = @Content(mediaType = "application/json")
             ),
             @ApiResponse(
                     responseCode = "401",
                     description = "Autentifikatsiya muvaffaqiyatsiz (token yo'q yoki yaroqsiz)",
                     content = @Content
             ),
             @ApiResponse(
                     responseCode = "403",
                     description = "Foydalanuvchida ADMIN huquqi yo'q",
                     content = @Content
             )
     })*/
    @Operation(
            summary = "Kun uchun qoshimcha bandlik kiritish",
            description = """
                    Admin tomonidan tanlangan sanani to'liq bloklaydi.
                    Ushbu kunda yangi buyurtmalar qabul qilinmaydi va mavjud buyurtmalar holati o'zgartirilmaydi.
                    Faqat admin huquqiga ega foydalanuvchilar ishlatishi mumkin."""
    )
    @PostMapping
    public HttpEntity<?> blockByAdmin(@RequestBody AdminBlockDto adminBlockDto) {
        AdminBlockValidator.validate(adminBlockDto.date());
        adminBlockCServiceApp.addAdditionalBlock(adminBlockDto);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "qoshimcha bandlikni bekor qilish ",
            description = """
                    
                    """
    )
    @DeleteMapping
    public HttpEntity<?> cancelBlock(@RequestParam LocalDate date) {
        AdminBlockValidator.validate(date);
        adminBlockCServiceApp.cancelBlock(date);
        return ResponseEntity.noContent().build();
    }
}
