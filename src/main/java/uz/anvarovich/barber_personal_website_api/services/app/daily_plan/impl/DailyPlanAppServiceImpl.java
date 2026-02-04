package uz.anvarovich.barber_personal_website_api.services.app.daily_plan;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.UpdateDailyPlanDto;
import uz.anvarovich.barber_personal_website_api.entity.DailyPlan;
import uz.anvarovich.barber_personal_website_api.services.domain.dailyPlan_service.DailyPlanService;
import uz.anvarovich.barber_personal_website_api.services.domain.time_slote.TimeSlotService;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DailyPlanAppServiceImpl implements DailyPlanAppService {
    private final DailyPlanService dailyPlanService;
    private final TimeSlotService timeSlotService;

    @Override
    public void updateDailyPlan(LocalDate date, UpdateDailyPlanDto updateDailyPlanDto) {
        DailyPlan updated = dailyPlanService.update(date, updateDailyPlanDto);
        timeSlotService.updateByDailyPlanId(updated, updateDailyPlanDto);
    }
}
