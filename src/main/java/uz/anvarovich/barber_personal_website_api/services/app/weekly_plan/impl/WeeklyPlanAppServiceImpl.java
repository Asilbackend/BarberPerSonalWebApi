package uz.anvarovich.barber_personal_website_api.services.app.weekly_plan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.SystemSettingDto;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.UpdateDailyPlanDto;
import uz.anvarovich.barber_personal_website_api.entity.DailyPlan;
import uz.anvarovich.barber_personal_website_api.entity.WeeklyPlan;
import uz.anvarovich.barber_personal_website_api.projection.TimeSlotProjection;
import uz.anvarovich.barber_personal_website_api.services.domain.dailyPlan_service.DailyPlanService;
import uz.anvarovich.barber_personal_website_api.services.domain.system_setting_service.SystemSettingService;
import uz.anvarovich.barber_personal_website_api.services.domain.time_slote.TimeSlotService;
import uz.anvarovich.barber_personal_website_api.services.domain.weekly_plan.WeeklyPlanService;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeeklyPlanAppServiceImpl implements WeeklyPlanAppService {
    private final WeeklyPlanService weeklyPlanService;
    private final SystemSettingService systemSettingService;
    private final DailyPlanService dailyPlanService;
    private final TimeSlotService timeSlotService;

    @Override
    @Transactional
    public void createNew() {
        LocalDate oldWeekStartDate = weeklyPlanService.findLastWeek()
                .map(WeeklyPlan::getWeekStartDate)
                .map(date -> date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)))
                .orElseGet(() -> LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)));

        SystemSettingDto currentSetting = systemSettingService.getCurrent();
        LocalDate newWeekStartDate = oldWeekStartDate.plusWeeks(1);
        WeeklyPlan weeklyPlan = weeklyPlanService.createWeeklyPlan(newWeekStartDate);
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
    public void createWeeklyPlanByLastWeek() {
        WeeklyPlan oldWeeklyPlan = weeklyPlanService.findLastWeek().orElseThrow();
        LocalDate oldWeeklyPlanWeekStartDate = oldWeeklyPlan.getWeekStartDate();
        LocalDate newWeekStartDate = oldWeeklyPlanWeekStartDate.plusWeeks(1);
        WeeklyPlan newWeeklyPlan = weeklyPlanService.createWeeklyPlan(newWeekStartDate);
        List<DailyPlan> oldDailyPlans = dailyPlanService.findAllByWeeklyPlanId(oldWeeklyPlan.getId());
        Map<DailyPlan, DailyPlan> oldAndNew = dailyPlanService.createDailyPlansByOld(oldDailyPlans, newWeeklyPlan);
        timeSlotService.createTimeSlotsByOld(oldAndNew);

    }
}
