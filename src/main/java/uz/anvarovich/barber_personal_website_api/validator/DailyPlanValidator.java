package uz.anvarovich.barber_personal_website_api.validator;

import org.springframework.http.HttpStatus;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.SystemSettingDto;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.UpdateDailyPlanDto;
import uz.anvarovich.barber_personal_website_api.handler.exceptions.CustomException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class DailyPlanValidator {
    private DailyPlanValidator() {

    }

    public static void validate(UpdateDailyPlanDto dto, LocalDate date) {
        if (!date.isAfter(LocalDate.now())) {
            throw new CustomException(
                    "ADMIN, siz ertangi kundan boshlab boshqa kunlarni update qila olasiz, bugunni emas",
                    HttpStatus.BAD_REQUEST,
                    "CANNOT_UPDATE_TODAY"
            );
        }

        Objects.requireNonNull(dto, "UpdateDailyPlanDto cannot be null");

        Objects.requireNonNull(dto.workStartTime(), "workStartTime is required");
        Objects.requireNonNull(dto.workEndTime(), "workEndTime is required");

        if (dto.workEndTime().isBefore(dto.workStartTime())) {
            throw new CustomException(
                    "Ish tugash vaqti boshlanish vaqtidan keyin bo‘lishi kerak",
                    HttpStatus.BAD_REQUEST,
                    "INVALID_TIME_RANGE"
            );
        }

        Objects.requireNonNull(dto.slotDurationMin(), "slotDurationMin is required");

        if (dto.slotDurationMin() < 5) {
            throw new CustomException(
                    "Slot davomiyligi kamida 5 daqiqa bo‘lishi kerak",
                    HttpStatus.BAD_REQUEST,
                    "SLOT_DURATION_TOO_SHORT"
            );
        }
    }
}
