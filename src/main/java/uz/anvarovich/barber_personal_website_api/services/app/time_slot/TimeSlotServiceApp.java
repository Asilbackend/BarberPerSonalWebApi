package uz.anvarovich.barber_personal_website_api.services.app.time_slot;

import uz.anvarovich.barber_personal_website_api.projection.TimeSlotProjection;

import java.time.LocalDate;
import java.util.List;

public interface TimeSlotServiceApp {
    List<TimeSlotProjection> findByDaily(LocalDate date);
}
