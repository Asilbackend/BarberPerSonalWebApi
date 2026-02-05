package uz.anvarovich.barber_personal_website_api.services.app.user_service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.CreateUserReqDto;
import uz.anvarovich.barber_personal_website_api.entity.user.User;
import uz.anvarovich.barber_personal_website_api.entity.user.enums.Role;
import uz.anvarovich.barber_personal_website_api.services.app.user_service.UserAppService;
import uz.anvarovich.barber_personal_website_api.services.domain.user_service.UserService;

@Service
@RequiredArgsConstructor
public class UserAppServiceImpl implements UserAppService {
    private final UserService userService;

    @Override
    public void registerUserByAdmin(CreateUserReqDto createUserReqDto, Role role) {
        if (role.equals(Role.ADMIN)) {
            userService.createAdmin(createUserReqDto);
        } else if (role.equals(Role.USER)) {
            userService.createUser(createUserReqDto);
        } else {
            throw new IllegalArgumentException("role not match");
        }
    }

    @Override
    public User getCurrentUser() {
        return userService.getCurrentUser();
    }

    @Override
    @Transactional
    public void createUser(CreateUserReqDto createUserReqDto) {
        userService.createUser(createUserReqDto);
    }
}
