package uz.anvarovich.barber_personal_website_api.validator;

import org.springframework.http.HttpStatus;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.SystemSettingDto;
import uz.anvarovich.barber_personal_website_api.entity.enums.SlotStatus;
import uz.anvarovich.barber_personal_website_api.entity.time_slot.TimeSlot;
import uz.anvarovich.barber_personal_website_api.handler.exceptions.CustomException;

import java.time.LocalDate;
import java.util.List;

public final class BookingValidator {
    private BookingValidator() {

    }

    public static void validate(LocalDate date, List<TimeSlot> timeSlots, boolean forAdmin, SystemSettingDto systemSettingDto) {
        if (date.isBefore(LocalDate.now())) {
            throw new CustomException(
                    "Bugundan oldingi kunlarni band qilolmaysiz",
                    HttpStatus.BAD_REQUEST,
                    "DATE_IN_PAST"
            );
        }

        if (!forAdmin) {
            Integer visibleDaysForUsers = systemSettingDto.visibleDaysForUsers();

            if (LocalDate.now().plusDays(visibleDaysForUsers).isBefore(date)) {
                throw new CustomException(
                        "Siz faqat %d kunlik planni ko‘rib booking qila olasiz".formatted(visibleDaysForUsers),
                        HttpStatus.BAD_REQUEST,
                        "DATE_BEYOND_VISIBLE_RANGE"
                );
            }

            if (timeSlots.size() > 2) {
                throw new CustomException(
                        "2 tadan ko‘p slot tanlay olmaysiz",
                        HttpStatus.BAD_REQUEST,
                        "TOO_MANY_SLOTS"
                );
            }

            if (timeSlots.size() == 2) {
                TimeSlot timeSlot1 = timeSlots.get(0);
                TimeSlot timeSlot2 = timeSlots.get(1);

                if (!timeSlot1.getEndTime().equals(timeSlot2.getStartTime())) {
                    throw new CustomException(
                            "Tanlangan slotlar ketma-ket emas",
                            HttpStatus.BAD_REQUEST,
                            "SLOTS_NOT_CONSECUTIVE"
                    );
                }
            }
        }

        for (TimeSlot timeSlot : timeSlots) {
            if (!timeSlot.getDailyPlan().getDate().equals(date)) {
                throw new CustomException(
                        "Time slotlar kiritilgan kun/sana bilan mos kelmayapti",
                        HttpStatus.BAD_REQUEST,
                        "SLOT_DATE_MISMATCH"
                );
            }

            if (!timeSlot.getSlotStatus().equals(SlotStatus.OPEN)) {
                throw new CustomException(
                        "Barcha tanlangan slotlar OPEN holatda bo‘lishi kerak",
                        HttpStatus.BAD_REQUEST,
                        "SLOT_NOT_OPEN"
                );
            }
        }
    }
}
