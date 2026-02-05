package uz.anvarovich.barber_personal_website_api.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.CreateUserReqDto;
import uz.anvarovich.barber_personal_website_api.entity.SystemSettings;
import uz.anvarovich.barber_personal_website_api.entity.WeeklyPlan;
import uz.anvarovich.barber_personal_website_api.repository.SystemSettingsRepository;
import uz.anvarovich.barber_personal_website_api.repository.UserRepository;
import uz.anvarovich.barber_personal_website_api.repository.WeeklyPlanRepository;
import uz.anvarovich.barber_personal_website_api.services.app.weekly_plan.WeeklyPlanAppService;
import uz.anvarovich.barber_personal_website_api.services.domain.user_service.UserService;

import java.time.LocalTime;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class Runner implements CommandLineRunner {
    private final SystemSettingsRepository systemSettingsRepository;
    @Value("${server.url}")
    private String serverUrl;
    private final WeeklyPlanRepository weeklyPlanRepository;
    private final WeeklyPlanAppService weeklyPlanAppService;
    private final UserService userService;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        createDefaultData();
        System.out.println("----------------------------------------------");
        System.out.println(serverUrl + "swagger-ui/index.html#/");
    }

    private void createDefaultData() {
        createIfNotAdminUsers();
        createIfNotSysTemSetting();
        createIfNotWeeklyPlan();
    }

    private void createIfNotWeeklyPlan() {
        Optional<WeeklyPlan> lastWeek = weeklyPlanRepository.findLastWeek();
        if (lastWeek.isEmpty()) {
            for (int i = 0; i < 1; i++) {
                weeklyPlanAppService.createNew();
            }
        }
    }

    private void createIfNotSysTemSetting() {
        if (systemSettingsRepository.findAll().isEmpty()) {
            SystemSettings systemSettings = new SystemSettings(7, LocalTime.of(9, 0), LocalTime.of(19, 0), 30);
            systemSettingsRepository.save(systemSettings);
        }
    }

    private void createIfNotAdminUsers() {
        if (userRepository.findAll().isEmpty()) {
            userService.createAdmin(new CreateUserReqDto("Asilbek O'ktamov", "998919207150", "admin200", "award200", "award200"));
            userService.createUser(new CreateUserReqDto("1Asilbekjon User1", "998339207150", "user200", "award200", "award200"));
            userService.createUser(new CreateUserReqDto("2Userjon User2", "998339207100", "user201", "award200", "award200"));
        }
    }
}
