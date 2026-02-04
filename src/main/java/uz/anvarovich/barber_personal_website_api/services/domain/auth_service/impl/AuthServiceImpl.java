package uz.anvarovich.barber_personal_website_api.services.domain.auth_service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import uz.anvarovich.barber_personal_website_api.entity.user.User;
import uz.anvarovich.barber_personal_website_api.repository.UserRepository;
import uz.anvarovich.barber_personal_website_api.services.domain.auth_service.AuthService;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    private User getAuthenticatedUser() {
        String username = getUserName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationCredentialsNotFoundException("User not found: " + username));
    }

    private String getUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AuthenticationCredentialsNotFoundException("No authentication found in context.");
        }

        if (!authentication.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("User is not authenticated.");
        }
        Object principal = authentication.getPrincipal();
        if (principal == null) {
            throw new AuthenticationCredentialsNotFoundException("User is not authenticated.");
        }
        return principal.toString();
    }

    @Override
    public User getAuthUser() {
        return getAuthenticatedUser();
    }

    @Override
    public Long getAuthUserId() {
        String userName = getUserName();
        return userRepository.findUserIdByUsername(userName).orElseThrow(() -> new AuthenticationCredentialsNotFoundException("Foydalanuvchi topilmadi"));
    }

    @Override
    public String getAuthUsername() {
        return getUserName();
    }

}
