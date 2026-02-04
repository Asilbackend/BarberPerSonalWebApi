package uz.anvarovich.barber_personal_website_api.services.domain.dailyPlan_service;

import uz.anvarovich.barber_personal_website_api.dto.req_dto.SystemSettingDto;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.UpdateDailyPlanDto;
import uz.anvarovich.barber_personal_website_api.dto.resp_dto.WeeklyPlanRespDto;
import uz.anvarovich.barber_personal_website_api.entity.DailyPlan;
import uz.anvarovich.barber_personal_website_api.entity.WeeklyPlan;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface DailyPlanService {

    DailyPlan cerateDailyPlan(LocalDate toDay, SystemSettingDto currentSetting, WeeklyPlan weeklyPlan);


    List<DailyPlan> findAllByWeeklyPlanId(Long id);

    Map<DailyPlan, DailyPlan> createDailyPlansByOld(List<DailyPlan> oldDailyPlans, WeeklyPlan newWeeklyPlan);

    DailyPlan findByDate(LocalDate date);

    DailyPlan update(LocalDate date, UpdateDailyPlanDto updateDailyPlanDto);


    boolean setDayOff(DailyPlan byDate);

    boolean cancelDayOff(DailyPlan dailyPlan);

}
