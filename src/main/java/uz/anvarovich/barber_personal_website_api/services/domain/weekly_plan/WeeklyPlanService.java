package uz.anvarovich.barber_personal_website_api.services.domain.weekly_plan;

import uz.anvarovich.barber_personal_website_api.entity.WeeklyPlan;

import java.time.LocalDate;
import java.util.Optional;

public interface WeeklyPlanService {
    Optional<WeeklyPlan> findLastWeek();

    WeeklyPlan createWeeklyPlan(LocalDate newWeekStartDate);

    boolean existsByWeekStartDate(LocalDate nextMonday);

}
