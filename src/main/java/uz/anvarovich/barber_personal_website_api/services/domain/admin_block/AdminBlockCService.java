package uz.anvarovich.barber_personal_website_api.services.domain.admin_block;

import uz.anvarovich.barber_personal_website_api.dto.AdminBlockDto;
import uz.anvarovich.barber_personal_website_api.entity.AdminBlock;
import uz.anvarovich.barber_personal_website_api.entity.DailyPlan;

import java.time.LocalDate;
import java.util.List;

public interface AdminBlockCService {

    void create(AdminBlockDto adminBlockDto, DailyPlan byDate);

    void deleteByDailyPlanId(Long dailyPlanId);

    AdminBlock findByDailyId(Long dailyId);
}
