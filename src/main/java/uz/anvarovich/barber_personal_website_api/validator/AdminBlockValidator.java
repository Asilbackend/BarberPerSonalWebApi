package uz.anvarovich.barber_personal_website_api.validator;

import java.time.LocalDate;

public final class AdminBlockValidator {
    private AdminBlockValidator() {
    }

    public static void validate(LocalDate date) {
        if (!date.isAfter(LocalDate.now())) {
            throw new RuntimeException("bugun dan keyingi kunlar uchun bandlik yarata olasiz");
        }
    }
}
