package uz.anvarovich.barber_personal_website_api.validator;

import org.springframework.http.HttpStatus;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.SystemSettingDto;
import uz.anvarovich.barber_personal_website_api.entity.enums.SlotStatus;
import uz.anvarovich.barber_personal_website_api.entity.time_slot.TimeSlot;
import uz.anvarovich.barber_personal_website_api.handler.exceptions.CustomException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public final class BookingValidator {
    private BookingValidator() {

    }

    public static void validate(
            LocalDate date,
            List<TimeSlot> timeSlots,
            boolean forAdmin,
            SystemSettingDto systemSettingDto
    ) {

        // Sana: bugun yoki kelajak bo‘lishi shart
        if (date.isBefore(LocalDate.now())) {
            throw new CustomException(
                    "Bugundan oldingi kunlarni band qilolmaysiz",
                    HttpStatus.BAD_REQUEST,
                    "DATE_IN_PAST"
            );
        }

        // User uchun qo‘shimcha cheklovlar
        if (!forAdmin) {

            Integer visibleDaysForUsers = systemSettingDto.visibleDaysForUsers();

            if (date.isAfter(LocalDate.now().plusDays(visibleDaysForUsers))) {
                throw new CustomException(
                        "Siz faqat %d kunlik planni ko‘rib booking qila olasiz"
                                .formatted(visibleDaysForUsers),
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
                timeSlots.sort(Comparator.comparing(TimeSlot::getStartTime));

                TimeSlot first = timeSlots.get(0);
                TimeSlot second = timeSlots.get(1);

                if (!first.getEndTime().equals(second.getStartTime())) {
                    throw new CustomException(
                            "Tanlangan slotlar ketma-ket emas",
                            HttpStatus.BAD_REQUEST,
                            "SLOTS_NOT_CONSECUTIVE"
                    );
                }
            }
        }

        //Har bir slot uchun tekshiruv
        for (TimeSlot timeSlot : timeSlots) {

            // Slot sanasi mos kelishi shart
            if (!timeSlot.getDailyPlan().getDate().equals(date)) {
                throw new CustomException(
                        "Time slotlar kiritilgan sana bilan mos kelmayapti",
                        HttpStatus.BAD_REQUEST,
                        "SLOT_DATE_MISMATCH"
                );
            }

            // Slot OPEN bo‘lishi shart
            if (timeSlot.getSlotStatus() != SlotStatus.OPEN) {
                throw new CustomException(
                        "Barcha tanlangan slotlar OPEN holatda bo‘lishi kerak",
                        HttpStatus.BAD_REQUEST,
                        "SLOT_NOT_OPEN"
                );
            }

            // Faqat user uchun: o‘tmish vaqtni band qilmaslik
            if (!forAdmin && date.equals(LocalDate.now())) {

                LocalDateTime slotDateTime =
                        LocalDateTime.of(date, timeSlot.getStartTime());

                if (slotDateTime.isBefore(LocalDateTime.now())) {
                    throw new CustomException(
                            "O‘tmishdagi vaqtni band qilolmaysiz",
                            HttpStatus.BAD_REQUEST,
                            "TIME_IN_PAST"
                    );
                }
            }
        }
    }
}
