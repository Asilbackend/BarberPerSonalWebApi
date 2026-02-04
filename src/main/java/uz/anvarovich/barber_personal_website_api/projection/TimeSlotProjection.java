package uz.anvarovich.barber_personal_website_api.projection;

import uz.anvarovich.barber_personal_website_api.entity.enums.SlotStatus;

import java.time.LocalTime;

public interface TimeSlotProjection {
    Long getTimeSlotId();

    LocalTime getStartTime();

    LocalTime getEndTime();

    SlotStatus getSlotStatus();

    Boolean getIsOutsideSchedule();
}
