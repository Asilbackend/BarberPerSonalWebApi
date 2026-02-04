package uz.anvarovich.barber_personal_website_api.services.domain.user_service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.CreateUserReqDto;
import uz.anvarovich.barber_personal_website_api.entity.user.User;
import uz.anvarovich.barber_personal_website_api.entity.user.enums.Role;
import uz.anvarovich.barber_personal_website_api.repository.UserRepository;
import uz.anvarovich.barber_personal_website_api.services.domain.auth_service.AuthService;
import uz.anvarovich.barber_personal_website_api.validator.UserValidator;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final AuthService authService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getCurrentUser() {
        return authService.getAuthUser();
    }

    @Override
    public Long getCurrentUserId() {
        return authService.getAuthUserId();
    }

    @Override
    public List<User> findAllAdmin() {
        return userRepository.findAllByRole(Role.ADMIN);
    }

    @Override
    @Transactional
    public void createAdmin(CreateUserReqDto createUserReqDto) {
        create(createUserReqDto, Role.ADMIN);
    }

    @Override
    @Transactional
    public void createUser(CreateUserReqDto createUserReqDto) {
        create(createUserReqDto, Role.USER);
    }

    private void create(CreateUserReqDto createUserReqDto, Role role) {
        UserValidator.validate(createUserReqDto);
        if (userRepository.findByUsername(createUserReqDto.username()).isPresent()) {
            throw new RuntimeException("this username already exist, please choose another one!");
        }
        String encodePassword = passwordEncoder.encode(createUserReqDto.password());
        User user = new User(createUserReqDto.fullName(), createUserReqDto.phone(), createUserReqDto.username(), role, encodePassword);
        userRepository.save(user);
    }
}
