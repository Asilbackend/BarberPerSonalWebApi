package uz.anvarovich.barber_personal_website_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.anvarovich.barber_personal_website_api.entity.time_slot.TimeSlot;
import uz.anvarovich.barber_personal_website_api.projection.TimeSlotProjection;

import java.time.LocalDate;
import java.util.List;

public interface TimeSlotRepository extends JpaRepository<TimeSlot, Long> {
    @Query(value = """
               SELECT ts.start_time          AS startTime,
                   ts.id                  AS timeSlotId,
                   ts.slot_status         AS slotStatus,
                   ts.end_time            as endTime,
                   ts.is_outside_schedule AS isOutsideSchedule
            FROM time_slot ts
                     INNER JOIN daily_plan dp ON ts.daily_plan_id = dp.id
            WHERE dp.date = :date
            ORDER BY ts.start_time;
                           """, nativeQuery = true)
    List<TimeSlotProjection> findByDaily(LocalDate date);

    @Query(value = """
            SELECT ts.*
            FROM time_slot ts
                     join daily_plan dp on dp.id = ts.daily_plan_id
                and dp.is_day_off = false
                and dp.date = :date
            WHERE ts.id IN (:ids)
            ORDER BY ts.id;
            """, nativeQuery = true)
    List<TimeSlot> findAllByIdsAndDate(@Param("ids") List<Long> ids, @Param("date") LocalDate date);


    List<TimeSlot> findAllByDailyPlanId(Long dailyPlanId);

    @Modifying
    @Query("DELETE FROM TimeSlot ts " +
            "WHERE ts.dailyPlan.id = :dailyPlanId " +
            "AND ts.slotStatus = 'OPEN'")
    void deleteAllOpenByDailyPlanId(@Param("dailyPlanId") Long dailyPlanId);

    List<TimeSlot> findByDailyPlanId(Long dailyPlanId);
}