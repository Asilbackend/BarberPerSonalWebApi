package uz.anvarovich.barber_personal_website_api.validator;

import uz.anvarovich.barber_personal_website_api.dto.req_dto.SystemSettingDto;
import uz.anvarovich.barber_personal_website_api.entity.enums.SlotStatus;
import uz.anvarovich.barber_personal_website_api.entity.time_slot.TimeSlot;

import java.time.LocalDate;
import java.util.List;

public final class BookingValidator {
    private BookingValidator() {

    }

    public static void validate(LocalDate date, List<TimeSlot> timeSlots, boolean forAdmin, SystemSettingDto systemSettingDto) {
        if (!forAdmin) {
            Integer visibleDaysForUsers = systemSettingDto.visibleDaysForUsers();
            if (LocalDate.now().plusDays(visibleDaysForUsers).isBefore(date)) {
                throw new RuntimeException("siz faqat %s kunlik planni ko'rib book olasiz, kiritilgan kun uchun sizga ruxsat yoq".formatted(visibleDaysForUsers));
            }
            if (timeSlots.size() > 2) {
                throw new RuntimeException("2 tadan kop slot tanlolmaysiz");
            }
            TimeSlot timeSlot1 = timeSlots.get(0);
            TimeSlot timeSlot2 = timeSlots.get(1);
            if (!timeSlot1.getEndTime().equals(timeSlot2.getStartTime())) {
                throw new RuntimeException("tanlangan slotlar ketma ket emas");
            }
        }
        for (TimeSlot timeSlot : timeSlots) {
            if (!timeSlot.getDailyPlan().getDate().equals(date)) {
                throw new RuntimeException("Time slotlar 1 kunga to'g'ri kelmayapti");
            }
            if (!timeSlot.getSlotStatus().equals(SlotStatus.OPEN)) {
                throw new RuntimeException("Barcha slotlar open bolishi kerak");
            }
        }
    }
}
