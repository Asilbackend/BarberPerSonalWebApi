package uz.anvarovich.barber_personal_website_api.validator;

import uz.anvarovich.barber_personal_website_api.dto.req_dto.DailyPlanDto;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class WeeklyPlanValidator {

    private WeeklyPlanValidator() {
    }

    public static void validateStartDate(LocalDate weekStartDate) {
        DayOfWeek dayOfWeek = weekStartDate.getDayOfWeek();
        DayOfWeek monday = DayOfWeek.MONDAY;
        if (!dayOfWeek.equals(monday)) {
            throw new IllegalArgumentException("siz kiritgan sana Dushanba emas!!");
        }
    }
}
