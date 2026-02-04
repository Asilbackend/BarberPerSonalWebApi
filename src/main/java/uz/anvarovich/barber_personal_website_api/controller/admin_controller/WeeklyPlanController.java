package uz.anvarovich.barber_personal_website_api.controller.admin_controller;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.anvarovich.barber_personal_website_api.dto.resp_dto.WeeklyPlanRespDto;
import uz.anvarovich.barber_personal_website_api.services.app.daily_plan.DailyPlanAppService;
import uz.anvarovich.barber_personal_website_api.services.app.weekly_plan.WeeklyPlanAppService;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/admin/weekly-plan")
@RequiredArgsConstructor
public class WeeklyPlanController {
    private final WeeklyPlanAppService weeklyPlanAppService;
    private final DailyPlanAppService dailyPlanAppService;

    @Operation(
            summary = """
                    Yangi haftalik plan yaratish.
                    """,
            description = """
                      Qulaylik => {
                               Qachonki ADMIN -> new weekly plan yaratmoqchi bolsa, default-setting asosida avtomatik yaratiladi,
                               Keyin qo'lda xohlagancha weekly planni update qilishi mumkin
                               }
                    """
    )
    @PostMapping
    public HttpEntity<?> createNewWeeklyPlan() {
        weeklyPlanAppService.createNew();
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = """
                    Avvalgi hafataga ko'ra weekly plan yaratish.
                    Agar avvalgi hafta weekly plan bolmasa xatolik beradi
                    """,
            description = """
                      Qulaylik => {
                               Qachonki ADMIN -> new weekly plan yaratmoqchi bolsa, default-setting asosida avtomatik yaratiladi,
                               Keyin qo'lda xohlagancha weekly planni update qilishi mumkin
                               }
                    """
    )
    @PostMapping("/by-old")
    public HttpEntity<?> createWeeklyPlanByLastWeek() {
        weeklyPlanAppService.createWeeklyPlanByLastWeek();
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public HttpEntity<?> getPlanWeekly(@RequestParam LocalDate weekStartDate) {
        WeeklyPlanRespDto weeklyPlanRespDto = dailyPlanAppService.getWeeklyPlanFromWeekStartDateForAdmin(weekStartDate);
        return ResponseEntity.ok(weeklyPlanRespDto);
    }
}
