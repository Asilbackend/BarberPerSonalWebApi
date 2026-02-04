package uz.anvarovich.barber_personal_website_api.services.app.auth;

import uz.anvarovich.barber_personal_website_api.dto.req_dto.LoginRequestDTO;
import uz.anvarovich.barber_personal_website_api.dto.resp_dto.LoginRespDto;
import uz.anvarovich.barber_personal_website_api.dto.resp_dto.UserRespDto;

public interface AuthUserServiceApp {
    LoginRespDto login(LoginRequestDTO loginRequestDTO);

    String refreshToken(String refreshToken);
    UserRespDto getAuthUser();

}
