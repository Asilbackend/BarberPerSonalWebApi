package uz.anvarovich.barber_personal_website_api.services.weekly_plan;

import uz.anvarovich.barber_personal_website_api.dto.req_dto.WeeklyPlanDto;

public interface WeeklyPlanService {

    void createNew(WeeklyPlanDto weeklyPlanDto);
}
