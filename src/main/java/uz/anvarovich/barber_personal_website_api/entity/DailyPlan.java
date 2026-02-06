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
        if (!(o instanceof DailyPlan that)) return false;

        if (this.getId() == null || that.getId() == null) return false;

        return this.getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }


    // ------------------------------------------------------------------------

    @Override
    public DailyPlan clone() {
        try {
            return (DailyPlan) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
