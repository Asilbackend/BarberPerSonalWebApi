package uz.anvarovich.barber_personal_website_api.entity.time_slot;

import jakarta.persistence.*;
import lombok.*;
import uz.anvarovich.barber_personal_website_api.entity.DailyPlan;
import uz.anvarovich.barber_personal_website_api.entity.abs.BaseEntity;
import uz.anvarovich.barber_personal_website_api.entity.enums.SlotStatus;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class TimeSlot extends BaseEntity {
    @ManyToOne
    private DailyPlan dailyPlan;
    private LocalTime startTime;
    private LocalTime endTime;
    @Enumerated(EnumType.STRING)
    private SlotStatus slotStatus = SlotStatus.OPEN;
    private Boolean isOutsideSchedule; // keyinchalik bu BOOKED qilingan slot vaqti ish vaqtiga to'g'ri kelmay qolsa uni ochrmaymiz, shunchaki shu yerga true qoyamiz xolos

   /*
   BOOKED slot:
  delete qilinmaydi
  o‘zgartirilmaydi
*/
}