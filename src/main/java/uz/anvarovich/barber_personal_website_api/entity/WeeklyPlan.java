package uz.anvarovich.barber_personal_website_api.entity;

import jakarta.persistence.Entity;
import lombok.*;
import uz.anvarovich.barber_personal_website_api.entity.abs.BaseEntity;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class WeeklyPlan extends BaseEntity {
    private LocalDate weekStartDate;
    private LocalDate weekEndDate;
    private Boolean isCopiedFromPrev;
}
