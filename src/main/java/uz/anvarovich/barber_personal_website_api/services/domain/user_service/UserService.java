package uz.anvarovich.barber_personal_website_api.services.domain.user_service;

import uz.anvarovich.barber_personal_website_api.dto.req_dto.CreateUserReqDto;
import uz.anvarovich.barber_personal_website_api.entity.user.User;

import java.util.List;

public interface UserService {
    User getCurrentUser();

    Long getCurrentUserId();

    List<User> findAllAdmin();

    void createAdmin(CreateUserReqDto createUserReqDto);

    void createUser(CreateUserReqDto createUserReqDto);

}
