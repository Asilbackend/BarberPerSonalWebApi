package uz.anvarovich.barber_personal_website_api.controller.user_controller;


import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uz.anvarovich.barber_personal_website_api.dto.resp_dto.WeeklyPlanRespDto;
import uz.anvarovich.barber_personal_website_api.services.app.daily_plan.DailyPlanAppService;

import java.time.LocalDate;
@Tag(
        name = "User - Weekly va Daily planlarni get qilish"
)
@RestController
@RequestMapping("/api/user/plan")
@RequiredArgsConstructor
public class ShowPlanController {
    private final DailyPlanAppService dailyPlanAppService;

    @GetMapping("/daily")
    public HttpEntity<?> getPlanDaily(@RequestParam LocalDate date) {
        WeeklyPlanRespDto.DayDto dto = dailyPlanAppService.getDailyByDate(date, false);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/weekly")
    public HttpEntity<?> getPlanWeekly(@RequestParam LocalDate weekStartDate) {
        WeeklyPlanRespDto weeklyPlanRespDto = dailyPlanAppService.getWeeklyPlanFromWeekStartDateForUser(weekStartDate);
        return ResponseEntity.ok(weeklyPlanRespDto);
    }
}