package uz.anvarovich.barber_personal_website_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.anvarovich.barber_personal_website_api.entity.WeeklyPlan;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface WeeklyPlanRepositoryorg extends JpaRepository<WeeklyPlan, Long> {
    @Query(value = """
            select *
            from weekly_plan wp
            order by wp.week_start_date desc
            limit 1;
            """, nativeQuery = true)
    Optional<WeeklyPlan> findLastWeek();

    // Joriy haftadan boshlab (shu haftani ham qo'shib) keyingi barcha weekly planlarni olish
    @Query("SELECT wp FROM WeeklyPlan wp " +
            "WHERE wp.weekStartDate >= :weekStartDate " +
            "ORDER BY wp.weekStartDate ASC")
    List<WeeklyPlan> findAllWithWeekStartAfterOrEqual(@Param("weekStartDate") LocalDate weekStartDate);
}