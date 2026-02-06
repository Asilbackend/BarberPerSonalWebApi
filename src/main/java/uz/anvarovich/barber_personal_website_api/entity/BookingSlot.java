package uz.anvarovich.barber_personal_website_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.anvarovich.barber_personal_website_api.entity.abs.BaseEntity;
import uz.anvarovich.barber_personal_website_api.entity.booking.Booking;
import uz.anvarovich.barber_personal_website_api.entity.time_slot.TimeSlot;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
public class BookingSlot extends BaseEntity {
    @ManyToOne(optional = false)
    private Booking booking;
    @ManyToOne(optional = false)
    private TimeSlot timeSlot;
    private int slotOrder; // 1 yoki 2
}
