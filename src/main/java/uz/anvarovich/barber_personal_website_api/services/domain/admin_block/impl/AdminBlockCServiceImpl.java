package uz.anvarovich.barber_personal_website_api.services.domain.admin_block;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.anvarovich.barber_personal_website_api.dto.AdminBlockDto;
import uz.anvarovich.barber_personal_website_api.entity.AdminBlock;
import uz.anvarovich.barber_personal_website_api.entity.DailyPlan;
import uz.anvarovich.barber_personal_website_api.repository.AdminBlockRepository;

@Service
@RequiredArgsConstructor
public class AdminBlockCServiceImpl implements AdminBlockCService {
    private final AdminBlockRepository adminBlockRepository;

    @Override
    @Transactional
    public void create(AdminBlockDto adminBlockDto, DailyPlan dailyPlan) {
        if (adminBlockRepository.findByDailyPlanId(dailyPlan.getId()).isPresent()) {
            throw new RuntimeException("Bu kun allaqachon admin tomonidan band qilingan");
        }
        adminBlockRepository.save(new AdminBlock(dailyPlan, adminBlockDto.reason()));
    }

    @Override
    @Transactional
    public void deleteByDailyPlanId(Long dailyPlanId) {
        AdminBlock adminBlock = adminBlockRepository.findByDailyPlanId(dailyPlanId).orElseThrow();
        adminBlockRepository.delete(adminBlock);
    }
}
