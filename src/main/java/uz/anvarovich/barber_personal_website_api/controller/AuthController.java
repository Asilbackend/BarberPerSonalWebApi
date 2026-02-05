package uz.anvarovich.barber_personal_website_api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.LoginRequestDTO;
import uz.anvarovich.barber_personal_website_api.dto.resp_dto.LoginRespDto;
import uz.anvarovich.barber_personal_website_api.services.app.auth.AuthUserServiceApp;

import java.util.Map;
@Tag(
        name = "Login , tokenni refresh qilish"
)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthUserServiceApp authUserService;

    @PostMapping("/login")
    public HttpEntity<LoginRespDto> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        LoginRespDto login = authUserService.login(loginRequestDTO);
        return ResponseEntity.ok(login);
    }


    @PostMapping("/refresh-token")
    public Map<String, String> getNewAccessToken(@RequestParam String refreshToken) {
        String accessToken = authUserService.refreshToken(refreshToken);
        return Map.of("accessToken", accessToken);
    }
}
