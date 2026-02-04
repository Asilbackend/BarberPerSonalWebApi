package uz.anvarovich.barber_personal_website_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.anvarovich.barber_personal_website_api.entity.DailyPlan;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DailyPlanRepository extends JpaRepository<DailyPlan, Long> {

    // Muayyan weekly plan ga tegishli barcha daily planlarni olish
    List<DailyPlan> findAllByWeeklyPlanId(Long weeklyPlanId);




    Optional<DailyPlan> findByDate(LocalDate date);
}