package uz.anvarovich.barber_personal_website_api.services.app.daily_plan.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.UpdateDailyPlanDto;
import uz.anvarovich.barber_personal_website_api.dto.resp_dto.WeeklyPlanRespDto;
import uz.anvarovich.barber_personal_website_api.entity.AdminBlock;
import uz.anvarovich.barber_personal_website_api.entity.DailyPlan;
import uz.anvarovich.barber_personal_website_api.projection.TimeSlotProjection;
import uz.anvarovich.barber_personal_website_api.repository.WeeklyPlanRepositoryJdbc;
import uz.anvarovich.barber_personal_website_api.services.app.daily_plan.DailyPlanAppService;
import uz.anvarovich.barber_personal_website_api.services.domain.admin_block.AdminBlockCService;
import uz.anvarovich.barber_personal_website_api.services.domain.dailyPlan_service.DailyPlanService;
import uz.anvarovich.barber_personal_website_api.services.domain.system_setting_service.SystemSettingService;
import uz.anvarovich.barber_personal_website_api.services.domain.time_slote.TimeSlotService;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DailyPlanAppServiceImpl implements DailyPlanAppService {
    private final DailyPlanService dailyPlanService;
    private final TimeSlotService timeSlotService;
    private final WeeklyPlanRepositoryJdbc weeklyPlanRepositoryJdbc;
    private final SystemSettingService systemSettingService;
    private final AdminBlockCService adminBlockCService;

    @Override
    public void updateDailyPlan(LocalDate date, UpdateDailyPlanDto updateDailyPlanDto) {
        DailyPlan updated = dailyPlanService.update(date, updateDailyPlanDto);
        timeSlotService.updateByDailyPlanId(updated, updateDailyPlanDto);
    }

    @Override
    public WeeklyPlanRespDto getWeeklyPlanFromWeekStartDateForUser(LocalDate weekStartDate) {
        return weeklyPlanRepositoryJdbc.getWeeklyPlan(weekStartDate, getPlusDayFromStartWeek(weekStartDate), false);
    }


    @Override
    public WeeklyPlanRespDto getWeeklyPlanFromWeekStartDateForAdmin(LocalDate weekStartDate) {
        return weeklyPlanRepositoryJdbc.getWeeklyPlan(weekStartDate, 5, true);
    }

    @Override
    public WeeklyPlanRespDto.DayDto getDailyByDate(LocalDate date, boolean admin) {
        /*DailyPlanValidator.validateDateIsVisible(date, systemSettingService.getCurrent());*/
        // ko'ra olsin ammo book qila olmasin
        DailyPlan dailyPlan = dailyPlanService.findByDate(date);
        List<TimeSlotProjection> timeSlotProjections = timeSlotService.findByDaily(date);
        String reason = getReasonIfDayOff(dailyPlan, admin);
        return new WeeklyPlanRespDto.DayDto(dailyPlan.getDate(), dailyPlan.getIsDayOff(), reason, timeSlotProjections);
    }

    private String getReasonIfDayOff(DailyPlan dailyPlan, boolean admin) {
        if (!admin) {
            return null;
        }
        String reason = null;
        if (dailyPlan.getIsDayOff()) {
            AdminBlock adminBlock = adminBlockCService.findByDailyId(dailyPlan.getId());
            reason = adminBlock.getReason();
        }
        return reason;
    }

    private int getPlusDayFromStartWeek(LocalDate weekStartDate) {
        Integer visibleDaysForUsers = systemSettingService.getCurrent().visibleDaysForUsers();
        int plusDayFromStartWeek = 5;
        LocalDate andWeek = weekStartDate.plusDays(plusDayFromStartWeek);
        LocalDate lasVisibleDay = LocalDate.now().plusDays(visibleDaysForUsers);
        if (lasVisibleDay.isAfter(andWeek)) {
            //hammasi yaxshi
        } else {
            int daysBetween = (int) Math.abs(ChronoUnit.DAYS.between(andWeek, lasVisibleDay));
            plusDayFromStartWeek = plusDayFromStartWeek - daysBetween;
        }
        return plusDayFromStartWeek;
    }

}
