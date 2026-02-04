package uz.anvarovich.barber_personal_website_api.services.domain.weekly_plan.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.anvarovich.barber_personal_website_api.entity.WeeklyPlan;
import uz.anvarovich.barber_personal_website_api.repository.WeeklyPlanRepository;
import uz.anvarovich.barber_personal_website_api.services.domain.weekly_plan.WeeklyPlanService;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeeklyPlanServiceImpl implements WeeklyPlanService {

    private final WeeklyPlanRepository weeklyPlanRepository;

    @Override
    @Transactional(readOnly = true)
    public Optional<WeeklyPlan> findLastWeek() {
        return weeklyPlanRepository.findLastWeek();
    }

    @Transactional
    public WeeklyPlan createWeeklyPlan(LocalDate newWeekStartDate) {
        WeeklyPlan weeklyPlan = new WeeklyPlan(newWeekStartDate, newWeekStartDate.plusDays(5), false);
        return weeklyPlanRepository.save(weeklyPlan);
    }

    @Override
    public boolean existsByWeekStartDate(LocalDate nextMonday) {
        return weeklyPlanRepository.existsByWeekStartDate(nextMonday);
    }


}
