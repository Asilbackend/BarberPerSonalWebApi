package uz.anvarovich.barber_personal_website_api.mapper;

import uz.anvarovich.barber_personal_website_api.dto.resp_dto.UserRespDto;
import uz.anvarovich.barber_personal_website_api.entity.user.User;

public final class UserMapper {
    private UserMapper() {

    }

    public static UserRespDto toDto(User authUser) {
        return new UserRespDto(authUser.getFullName(), authUser.getPhone(), authUser.getUsername(), authUser.getRole().name());
    }
}
