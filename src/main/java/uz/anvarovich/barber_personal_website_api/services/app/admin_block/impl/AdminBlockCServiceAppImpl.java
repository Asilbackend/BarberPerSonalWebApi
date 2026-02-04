package uz.anvarovich.barber_personal_website_api.services.app.admin_block.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.anvarovich.barber_personal_website_api.dto.AdminBlockDto;
import uz.anvarovich.barber_personal_website_api.entity.DailyPlan;
import uz.anvarovich.barber_personal_website_api.services.app.admin_block.AdminBlockCServiceApp;
import uz.anvarovich.barber_personal_website_api.services.domain.admin_block.AdminBlockCService;
import uz.anvarovich.barber_personal_website_api.services.domain.dailyPlan_service.DailyPlanService;
import uz.anvarovich.barber_personal_website_api.services.domain.time_slote.TimeSlotService;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AdminBlockCServiceAppImpl implements AdminBlockCServiceApp {
    private final AdminBlockCService adminBlockCService;
    private final DailyPlanService dailyPlanService;
    private final TimeSlotService timeSlotService;

    @Override
    @Transactional
    public void addAdditionalBlock(AdminBlockDto adminBlockDto) {
        DailyPlan byDate = dailyPlanService.findByDate(adminBlockDto.date());
        boolean success = dailyPlanService.setDayOff(byDate);
        if (!success) {
            return;
        }
        adminBlockCService.create(adminBlockDto, byDate);
        timeSlotService.blockByDailyPlanId(byDate.getId());
    }

    @Override
    public void cancelBlock(LocalDate date) {
        DailyPlan dailyPlan = dailyPlanService.findByDate(date);
        boolean success = dailyPlanService.cancelDayOff(dailyPlan);
        if (!success) {
            return;
        }
        adminBlockCService.deleteByDailyPlanId(dailyPlan.getId());
        timeSlotService.cancelBlockByDailyPlanId(dailyPlan.getId());
    }
}
