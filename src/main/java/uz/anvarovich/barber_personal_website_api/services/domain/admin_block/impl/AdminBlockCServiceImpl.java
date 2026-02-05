package uz.anvarovich.barber_personal_website_api.services.domain.admin_block.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.anvarovich.barber_personal_website_api.dto.AdminBlockDto;
import uz.anvarovich.barber_personal_website_api.entity.AdminBlock;
import uz.anvarovich.barber_personal_website_api.entity.DailyPlan;
import uz.anvarovich.barber_personal_website_api.handler.exceptions.AlreadyExist;
import uz.anvarovich.barber_personal_website_api.repository.AdminBlockRepository;
import uz.anvarovich.barber_personal_website_api.services.domain.admin_block.AdminBlockCService;

@Service
@RequiredArgsConstructor
public class AdminBlockCServiceImpl implements AdminBlockCService {
    private final AdminBlockRepository adminBlockRepository;

    @Override
    @Transactional
    public void create(AdminBlockDto adminBlockDto, DailyPlan dailyPlan) {
        if (adminBlockRepository.findByDailyPlanId(dailyPlan.getId()).isPresent()) {
            throw new AlreadyExist("Bu kun allaqachon admin tomonidan band qilingan");
        }
        adminBlockRepository.save(new AdminBlock(dailyPlan, adminBlockDto.reason()));
    }

    @Override
    @Transactional
    public void deleteByDailyPlanId(Long dailyPlanId) {
        AdminBlock adminBlock = findByDailyId(dailyPlanId);
        adminBlockRepository.delete(adminBlock);
    }


    @Override
    public AdminBlock findByDailyId(Long dailyId) {
        return adminBlockRepository.findByDailyPlanId(dailyId).orElseThrow(() -> new EntityNotFoundException("dailyPlanId boyicha admin block topilmadi"));
    }

}
