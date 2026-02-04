package uz.anvarovich.barber_personal_website_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.anvarovich.barber_personal_website_api.entity.AdminBlock;
import uz.anvarovich.barber_personal_website_api.entity.DailyPlan;

import java.util.Optional;

public interface AdminBlockRepository extends JpaRepository<AdminBlock, Long> {
    Optional<AdminBlock> findByDailyPlanId(Long dailyPlanId);
}