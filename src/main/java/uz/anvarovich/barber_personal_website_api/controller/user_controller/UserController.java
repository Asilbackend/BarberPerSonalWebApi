package uz.anvarovich.barber_personal_website_api.controller.user_controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.CreateUserReqDto;
import uz.anvarovich.barber_personal_website_api.dto.resp_dto.UserRespDto;
import uz.anvarovich.barber_personal_website_api.mapper.UserMapper;
import uz.anvarovich.barber_personal_website_api.services.app.user_service.UserAppService;

@Tag(
        name = "User - registratsiya, login qilgan userni get qilish",
        description = "Admin tomonidan ma'lum kunlarni bloklash va blokni bekor qilish operatsiyalari"
)
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserAppService userAppService;

    @GetMapping
    public HttpEntity<?> getCurrentUser() {
        UserRespDto dto = UserMapper.toDto(userAppService.getCurrentUser());
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/register")
    public HttpEntity<?> registerUser(@RequestBody CreateUserReqDto createUserReqDto) {
        userAppService.createUser(createUserReqDto);
        return ResponseEntity.noContent().build();
    }
}
