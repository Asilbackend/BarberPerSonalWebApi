package uz.anvarovich.barber_personal_website_api.controller.common;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.anvarovich.barber_personal_website_api.projection.TimeSlotProjection;
import uz.anvarovich.barber_personal_website_api.services.app.time_slot.TimeSlotServiceApp;

import java.time.LocalDate;
import java.util.List;

/*@RestController
@RequestMapping("/api/common/plan")
@RequiredArgsConstructor*/
public class PublicScheduleController {
  /*  private final TimeSlotServiceApp timeSlotService;

    @GetMapping("/daily")//
    public HttpEntity<List<TimeSlotProjection>> getPlanDaily(@RequestParam LocalDate date) {
        List<TimeSlotProjection> res = timeSlotService.findByDaily(date);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/weekly")//
    public HttpEntity<List<TimeSlotProjection>> getPlanWeekly(@RequestParam LocalDate date) {
        List<TimeSlotProjection> res = timeSlotService.findByDaily(date);
        return ResponseEntity.ok(res);
    }
*/
    /*@GetMapping("/time-slots/daily")//
    public HttpEntity<List<TimeSlotProjection>> getTimeSlotsDaily(@RequestParam LocalDate date) {
        List<TimeSlotProjection> res = timeSlotService.findByDaily(date);
        return ResponseEntity.ok(res);
    }*/
}
