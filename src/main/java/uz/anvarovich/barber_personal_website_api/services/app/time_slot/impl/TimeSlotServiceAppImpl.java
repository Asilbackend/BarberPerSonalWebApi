package uz.anvarovich.barber_personal_website_api.services.app.time_slot.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.anvarovich.barber_personal_website_api.projection.TimeSlotProjection;
import uz.anvarovich.barber_personal_website_api.services.app.time_slot.TimeSlotServiceApp;
import uz.anvarovich.barber_personal_website_api.services.domain.time_slote.TimeSlotService;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TimeSlotServiceAppImpl implements TimeSlotServiceApp {
    private final TimeSlotService timeSlotService;

    @Override
    public List<TimeSlotProjection> findByDaily(LocalDate date) {
        return timeSlotService.findByDaily(date);
    }
}
