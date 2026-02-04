package uz.anvarovich.barber_personal_website_api.services.domain.time_slote;

import uz.anvarovich.barber_personal_website_api.dto.req_dto.BookDto;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.UpdateDailyPlanDto;
import uz.anvarovich.barber_personal_website_api.entity.DailyPlan;
import uz.anvarovich.barber_personal_website_api.entity.time_slot.TimeSlot;
import uz.anvarovich.barber_personal_website_api.projection.TimeSlotProjection;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TimeSlotService {
    List<TimeSlotProjection> findByDaily(LocalDate date);

    List<TimeSlot> findByDailyPlanId(Long dailyPlanId);

    List<TimeSlot> createTimeSlots(DailyPlan dailyPlan, UpdateDailyPlanDto dailyPlanDto);


    List<TimeSlot> findAllByIdsAndDate(List<Long> longs, LocalDate date);



    List<TimeSlot> findAllByDailyPlanId(Long id);

    void deleteAllOpenSlotsByDailyPlanId(Long id);

    void createTimeSlotsByOld(Map<DailyPlan, DailyPlan> oldAndNew);

    void updateByDailyPlanId(DailyPlan dailyPlanId, UpdateDailyPlanDto updateDailyPlanDto);

    void blockByDailyPlanId(Long dailyPlanId);

    void cancelBlockByDailyPlanId(Long dailyPlanId);

    List<TimeSlot> book(BookDto dto, boolean isAdmin);

    void cancelBookByAdmin(BookDto bookDto);

    void cancelSlotsByUser(List<TimeSlot> timeSlotsByBookingId);


}
