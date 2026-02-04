package uz.anvarovich.barber_personal_website_api.services.dailyPlan_service;

import uz.anvarovich.barber_personal_website_api.dto.req_dto.SystemSettingDto;
import uz.anvarovich.barber_personal_website_api.entity.DailyPlan;
import uz.anvarovich.barber_personal_website_api.entity.WeeklyPlan;

import java.time.LocalDate;

public interface DailyPlanService {



    DailyPlan cerateDailyPlan(LocalDate toDay, SystemSettingDto currentSetting, WeeklyPlan weeklyPlan);
}
