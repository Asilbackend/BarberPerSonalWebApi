package uz.anvarovich.barber_personal_website_api.entity.enums;

import jakarta.persistence.*;
import lombok.*;
import uz.anvarovich.barber_personal_website_api.entity.abs.BaseEntity;
import uz.anvarovich.barber_personal_website_api.entity.booking.BookingStatus;
import uz.anvarovich.barber_personal_website_api.entity.user.User;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(
        name = "booking",  // agar table nomini o'zgartirmoqchi bo'lsangiz
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_booking_user_date",
                        columnNames = {"user_id", "date"}
                )
        }
)
public class Booking extends BaseEntity {
    @ManyToOne
    private User user;
    private LocalDate date;
    private Integer slotCount;
    @Enumerated(EnumType.STRING)
    private BookingStatus status = BookingStatus.ACTIVE;
}
