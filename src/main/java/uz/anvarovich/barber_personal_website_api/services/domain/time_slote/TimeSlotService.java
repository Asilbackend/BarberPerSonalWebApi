package uz.anvarovich.barber_personal_website_api.services.time_slote;

import uz.anvarovich.barber_personal_website_api.dto.req_dto.SystemSettingDto;
import uz.anvarovich.barber_personal_website_api.entity.DailyPlan;
import uz.anvarovich.barber_personal_website_api.projection.TimeSlotProjection;

import java.time.LocalDate;
import java.util.List;

public interface TimeSlotService {

    List<TimeSlotProjection> findByDaily(LocalDate date);

    void createTimeSlots(DailyPlan dailyPlan, SystemSettingDto currentSetting);
}
