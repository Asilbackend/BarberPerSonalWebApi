package uz.anvarovich.barber_personal_website_api.controller.admin_controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.SystemSettingDto;
import uz.anvarovich.barber_personal_website_api.services.domain.system_setting_service.SystemSettingService;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {
    private final SystemSettingService systemSettingService;

    @Operation(
            summary = """
                    Bu default setting ni update qilish.
                    User ga korsatiladigan kunlar ham  shuni ichida
                    """,
            description = """
                      Qulaylik => {
                               Qachonki ADMIN -> new weekly plan yaratmoqchi bolsa shu default setting asosida avtomatik yaratiladi,
                               Keyin qo'lda xohlagancha weekly planni update qilishi mumkin
                               }
                    """
    )
    @PutMapping("/default-setting")
    public HttpEntity<?> updateSystemSetting(@RequestBody SystemSettingDto systemSettingDto) {
        systemSettingService.updateSystemSetting(systemSettingDto);
        //ertadan boshlab va keyingi haftalar update bolsin!
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = """
                    Bu default setting ni get qilish.
                    """,
            description = """
                      Qulaylik => {
                               Qachonki ADMIN -> new weekly plan yaratmoqchi bolsa shu default setting asosida avtomatik yaratiladi,
                               Keyin qo'lda xohlagancha weekly planni update qilishi mumkin
                               }
                    """
    )
    @GetMapping("/default-setting")
    public HttpEntity<SystemSettingDto> getSystemSettingCurrent() {
        SystemSettingDto systemSettingDto = systemSettingService.getCurrent();
        return ResponseEntity.ok(systemSettingDto);
    }

}
