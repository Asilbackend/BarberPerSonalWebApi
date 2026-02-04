package uz.anvarovich.barber_personal_website_api.entity;

import jakarta.persistence.Entity;
import lombok.*;
import uz.anvarovich.barber_personal_website_api.entity.abs.BaseEntity;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class SystemSettings extends BaseEntity {
    private Integer visibleDaysForUsers; //2,3,7,
    private LocalTime defaultWorkStartTime;
    private LocalTime defaultWorkEndTime;
    private Integer defaultSlotDurationMin;
}