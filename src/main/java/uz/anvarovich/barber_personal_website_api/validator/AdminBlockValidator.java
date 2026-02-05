package uz.anvarovich.barber_personal_website_api.validator;

import org.springframework.http.HttpStatus;
import uz.anvarovich.barber_personal_website_api.handler.exceptions.CustomException;

import java.time.LocalDate;

public final class AdminBlockValidator {
    private AdminBlockValidator() {
    }

    public static void validate(LocalDate date) {
        if (!date.isAfter(LocalDate.now())) {
            throw new CustomException(
                    "Bugundan keyingi kunlar uchungina bandlik yarata olasiz",
                    HttpStatus.BAD_REQUEST,
                    "INVALID_DATE_PAST_OR_TODAY"
            );
        }
    }
}
