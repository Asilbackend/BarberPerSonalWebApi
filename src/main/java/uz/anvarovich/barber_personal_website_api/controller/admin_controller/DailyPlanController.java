package uz.anvarovich.barber_personal_website_api.controller.admin_controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.UpdateDailyPlanDto;
import uz.anvarovich.barber_personal_website_api.dto.resp_dto.WeeklyPlanRespDto;
import uz.anvarovich.barber_personal_website_api.services.app.daily_plan.DailyPlanAppService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/admin/dailyPlan")
@RequiredArgsConstructor
public class DailyPlanController {
    private final DailyPlanAppService dailyPlanAppService;

    @Operation(
            summary = """
                    Kunlik plan ni update qilsh.
                    (date > bugun) shart bajarilishi kerak.
                    """,
            description = """
                    Daily planni update qilganda timeSlotlar qayta taqsimalanadi,
                    Agar client band qilgan timeSlotlar yangi taqsimlangan slotlar bilan mos kelmasa,
                    vaqtdan tashqari timeSlot sifatida belgilanadi va client rad etilmaydi.
                  
                    """
    )
    @PutMapping
    public HttpEntity<?> updateDailyPlan(@RequestParam LocalDate date, @RequestBody UpdateDailyPlanDto updateDailyPlanDto) {
        dailyPlanAppService.updateDailyPlan(date, updateDailyPlanDto);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public HttpEntity<?> getPlanDaily(@RequestParam LocalDate date) {
        WeeklyPlanRespDto.DayDto dto = dailyPlanAppService.getDailyByDate(date, true);
        return ResponseEntity.ok(dto);
    }

}
