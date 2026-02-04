package uz.anvarovich.barber_personal_website_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;
import uz.anvarovich.barber_personal_website_api.entity.abs.BaseEntity;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class AdminBlock extends BaseEntity {
    //Admin qo‘lda band qilgan vaqtlar (ish, shaxsiy ish)
    //Block kiritilganda: Shu oraliqdagi time slotlar =>  time_slot.status = BLOCKED
    @OneToOne
    private DailyPlan dailyPlan;
    private String reason;
}
