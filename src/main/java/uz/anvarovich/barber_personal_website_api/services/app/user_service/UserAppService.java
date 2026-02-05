package uz.anvarovich.barber_personal_website_api.services.app.user_service;

import uz.anvarovich.barber_personal_website_api.dto.req_dto.CreateUserReqDto;
import uz.anvarovich.barber_personal_website_api.entity.user.User;
import uz.anvarovich.barber_personal_website_api.entity.user.enums.Role;

public interface UserAppService {
    void registerUserByAdmin(CreateUserReqDto createUserReqDto, Role role);

    User getCurrentUser();

    void createUser(CreateUserReqDto createUserReqDto);
}
