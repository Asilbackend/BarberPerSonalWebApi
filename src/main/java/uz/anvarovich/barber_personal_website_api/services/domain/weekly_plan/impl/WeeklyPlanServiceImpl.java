package uz.anvarovich.barber_personal_website_api.services.domain.weekly_plan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.anvarovich.barber_personal_website_api.entity.WeeklyPlan;
import uz.anvarovich.barber_personal_website_api.repository.WeeklyPlanRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WeeklyPlanServiceImpl implements WeeklyPlanService {

    private final WeeklyPlanRepository weeklyPlanRepository;

    @Override
    public Optional<WeeklyPlan> findLastWeek() {

        return weeklyPlanRepository.findLastWeek();
    }

    @Transactional
    public WeeklyPlan createWeeklyPlan(LocalDate newWeekStartDate) {
        WeeklyPlan weeklyPlan = new WeeklyPlan(newWeekStartDate, newWeekStartDate.plusDays(5), false);
        return weeklyPlanRepository.save(weeklyPlan);
    }

    @Override
    public List<WeeklyPlan> findAllFromThisWeek() {
        return null;
    }

    @Override
    public List<WeeklyPlan> findAllWithWeekStartAfterOrEqual(LocalDate weekStart) {
        return weeklyPlanRepository.findAllWithWeekStartAfterOrEqual(weekStart);
    }
}
