package uz.anvarovich.barber_personal_website_api.controller.user_controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.CreateUserReqDto;
import uz.anvarovich.barber_personal_website_api.dto.resp_dto.UserRespDto;
import uz.anvarovich.barber_personal_website_api.mapper.UserMapper;
import uz.anvarovich.barber_personal_website_api.services.domain.user_service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @GetMapping
    public HttpEntity<?> getCurrentUser() {
        UserRespDto dto = UserMapper.toDto(userService.getCurrentUser());
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/register")
    public HttpEntity<?> registerUser(@RequestBody CreateUserReqDto createUserReqDto) {
        userService.createUser(createUserReqDto);
        return ResponseEntity.noContent().build();
    }
}
