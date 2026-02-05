package uz.anvarovich.barber_personal_website_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.anvarovich.barber_personal_website_api.entity.abs.BaseEntity;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class DailyPlan extends BaseEntity implements Cloneable {
    @Column(unique = true, nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private LocalTime workStartTime;
    @Column(nullable = false)
    private LocalTime workEndTime;
    @Column(nullable = false)
    private Integer slotDurationMin; //30 min
    private Boolean isDayOff;
    @ManyToOne(optional = false)
    private WeeklyPlan weeklyPlan;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DailyPlan dailyPlan = (DailyPlan) o;

        // ID null bo'lishi mumkin (yangi ob'ektlar uchun)
        return getId() != null && getId().equals(dailyPlan.getId());
    }

    @Override
    public int hashCode() {
        // ID null bo'lsa → standart constant (42 yoki boshqa son)
        return getId() != null ? getId().hashCode() : 42;
    }

    // ------------------------------------------------------------------------

    @Override
    public DailyPlan clone() {
        try {
            DailyPlan clone = (DailyPlan) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
