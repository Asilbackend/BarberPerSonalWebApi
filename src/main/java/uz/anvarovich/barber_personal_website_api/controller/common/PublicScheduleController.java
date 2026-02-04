package uz.anvarovich.barber_personal_website_api.controller.common;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.anvarovich.barber_personal_website_api.projection.TimeSlotProjection;
import uz.anvarovich.barber_personal_website_api.services.app.time_slot.TimeSlotServiceApp;
import uz.anvarovich.barber_personal_website_api.services.domain.time_slote.TimeSlotService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/common")
@RequiredArgsConstructor
public class CommonController {
    private final TimeSlotServiceApp timeSlotService;

    @GetMapping("/time-slots/daily")//
    public HttpEntity<List<TimeSlotProjection>> getTimeSlotsDaily(@RequestParam LocalDate date) {
        List<TimeSlotProjection> res = timeSlotService.findByDaily(date);
        return ResponseEntity.ok(res);
    }

    /*@GetMapping("/time-slots/daily")//
    public HttpEntity<List<TimeSlotProjection>> getTimeSlotsDaily(@RequestParam LocalDate date) {
        List<TimeSlotProjection> res = timeSlotService.findByDaily(date);
        return ResponseEntity.ok(res);
    }*/
}
