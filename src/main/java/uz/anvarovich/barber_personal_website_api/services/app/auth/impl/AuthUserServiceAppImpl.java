package uz.anvarovich.barber_personal_website_api.services.app.auth.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.LoginRequestDTO;
import uz.anvarovich.barber_personal_website_api.dto.resp_dto.LoginRespDto;
import uz.anvarovich.barber_personal_website_api.dto.resp_dto.UserRespDto;
import uz.anvarovich.barber_personal_website_api.mapper.UserMapper;
import uz.anvarovich.barber_personal_website_api.security.security.JwtUtils;
import uz.anvarovich.barber_personal_website_api.services.app.auth.AuthUserServiceApp;
import uz.anvarovich.barber_personal_website_api.services.domain.auth_service.AuthService;

@Service
@RequiredArgsConstructor
public class AuthUserServiceAppImpl implements AuthUserServiceApp {
    private final AuthService authService;
    private final JwtUtils jwtUtil;
    private final AuthenticationManager authenticationManager;


    @Override
    public String refreshToken(String refreshToken) {
        return jwtUtil.regenerateAccessToken(refreshToken);
    }

    public LoginRespDto login(LoginRequestDTO loginRequestDTO) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDTO.login(),
                            loginRequestDTO.password()
                    )
            );
            UserDetails userDetails = (UserDetails) authenticate.getPrincipal();
            String accessToken = jwtUtil.generateAccessToken(userDetails.getUsername());
            String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());
            return new LoginRespDto(accessToken, refreshToken);
        } catch (UsernameNotFoundException ex) {
            throw new UsernameNotFoundException("Login topilmadi"); // 404 yoki 401
        } catch (BadCredentialsException ex) {
            throw new BadCredentialsException("Parol noto'g'ri"); // 401
        } catch (DisabledException ex) {
            throw new DisabledException("Hisob faollashtirilmagan"); // 403
        } catch (LockedException ex) {
            throw new LockedException("Hisob bloklangan"); // 403
        }
    }

    @Override
    public UserRespDto getAuthUser() {
        return UserMapper.toDto(authService.getAuthUser());
    }
}
