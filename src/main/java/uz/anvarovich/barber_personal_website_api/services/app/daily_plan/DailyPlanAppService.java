package uz.anvarovich.barber_personal_website_api.services.app.daily_plan;

import uz.anvarovich.barber_personal_website_api.dto.req_dto.UpdateDailyPlanDto;
import uz.anvarovich.barber_personal_website_api.dto.resp_dto.WeeklyPlanRespDto;

import java.time.LocalDate;

public interface DailyPlanAppService {
    void updateDailyPlan(LocalDate date, UpdateDailyPlanDto updateDailyPlanDto);

    WeeklyPlanRespDto getWeeklyPlanFromWeekStartDateForUser(LocalDate weekStartDate);
    WeeklyPlanRespDto getWeeklyPlanFromWeekStartDateForAdmin(LocalDate weekStartDate);

    WeeklyPlanRespDto.DayDto getDailyByDate(LocalDate date, boolean admin);
}
