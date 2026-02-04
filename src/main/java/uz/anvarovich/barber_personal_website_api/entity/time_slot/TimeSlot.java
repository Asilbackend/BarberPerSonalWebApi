package uz.anvarovich.barber_personal_website_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.anvarovich.barber_personal_website_api.entity.abs.BaseEntity;
import uz.anvarovich.barber_personal_website_api.entity.enums.Status;

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
    private LocalTime start_time;
    private LocalTime end_time;
    private Status status = Status.OPEN;
}