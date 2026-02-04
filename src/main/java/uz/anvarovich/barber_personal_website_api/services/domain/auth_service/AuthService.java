package uz.anvarovich.barber_personal_website_api.services.domain.auth_service;

import uz.anvarovich.barber_personal_website_api.entity.user.User;

public interface AuthService {
    String getAuthUsername();
    User getAuthUser();
    Long getAuthUserId();

}
