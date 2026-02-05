package uz.anvarovich.barber_personal_website_api.services.app.weekly_plan.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.SystemSettingDto;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.UpdateDailyPlanDto;
import uz.anvarovich.barber_personal_website_api.entity.DailyPlan;
import uz.anvarovich.barber_personal_website_api.entity.WeeklyPlan;
import uz.anvarovich.barber_personal_website_api.entity.user.User;
import uz.anvarovich.barber_personal_website_api.services.app.weekly_plan.WeeklyPlanAppService;
import uz.anvarovich.barber_personal_website_api.services.domain.dailyPlan_service.DailyPlanService;
import uz.anvarovich.barber_personal_website_api.services.domain.notification.NotificationService;
import uz.anvarovich.barber_personal_website_api.services.domain.system_setting_service.SystemSettingService;
import uz.anvarovich.barber_personal_website_api.services.domain.time_slote.TimeSlotService;
import uz.anvarovich.barber_personal_website_api.services.domain.user_service.UserService;
import uz.anvarovich.barber_personal_website_api.services.domain.weekly_plan.WeeklyPlanService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class WeeklyPlanAppServiceImpl implements WeeklyPlanAppService {
    private final WeeklyPlanService weeklyPlanService;
    private final SystemSettingService systemSettingService;
    private final DailyPlanService dailyPlanService;
    private final TimeSlotService timeSlotService;
    private final NotificationService notificationService;
    private final UserService userService;

    @Override
    @Transactional
    public void createNew() {
        LocalDate oldWeekStartDate = weeklyPlanService.findLastWeek()
                .map(WeeklyPlan::getWeekStartDate)
                .map(date -> date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)))
                .orElseGet(() -> LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)).minusWeeks(1));

        SystemSettingDto currentSetting = systemSettingService.getCurrent();
        LocalDate newWeekStartDate = oldWeekStartDate.plusWeeks(1);
        WeeklyPlan weeklyPlan = weeklyPlanService.createWeeklyPlan(newWeekStartDate, false);
        for (int i = 0; i <= 5; i++) {
            LocalDate toDay = newWeekStartDate.plusDays(i);
            DailyPlan dailyPlan = dailyPlanService.cerateDailyPlan(toDay, currentSetting, weeklyPlan);
            UpdateDailyPlanDto updateDailyPlanDto = new UpdateDailyPlanDto(
                    currentSetting.defaultWorkStartTime(),
                    currentSetting.defaultWorkEndTime(),
                    currentSetting.defaultSlotDurationMin()
            );
            timeSlotService.createTimeSlots(dailyPlan, updateDailyPlanDto);
        }
    }

    @Override
    @Transactional
    public void createWeeklyPlanByLastWeek() {
        WeeklyPlan oldWeeklyPlan = weeklyPlanService.findLastWeek().orElseThrow(() -> new EntityNotFoundException("old weekly plan topilmadi"));
        LocalDate oldWeeklyPlanWeekStartDate = oldWeeklyPlan.getWeekStartDate();
        LocalDate newWeekStartDate = oldWeeklyPlanWeekStartDate.plusWeeks(1);
        WeeklyPlan newWeeklyPlan = weeklyPlanService.createWeeklyPlan(newWeekStartDate, true);
        List<DailyPlan> oldDailyPlans = dailyPlanService.findAllByWeeklyPlanId(oldWeeklyPlan.getId());
        Map<DailyPlan, DailyPlan> oldAndNew = dailyPlanService.createDailyPlansByOld(oldDailyPlans, newWeeklyPlan);
        timeSlotService.createTimeSlotsByOld(oldAndNew);
    }

    @Scheduled(cron = "0 0 18 ? * SAT") // har shanba 18:00*/
    public void checkNextWeekPlan() {
        LocalDate nextMonday = LocalDate.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        boolean exists = weeklyPlanService
                .existsByWeekStartDate(nextMonday);
        if (!exists) {
            List<User> admins = userService.findAllAdmin();
            notificationService.notifyAdmin(
                    "Keyingi hafta uchun plan yaratilmagan, Yaratib qo'yishni unutmang ☺",
                    admins
            );
        }
    }
}
