package uz.anvarovich.barber_personal_website_api.entity;

import jakarta.persistence.ManyToOne;
import uz.anvarovich.barber_personal_website_api.entity.user.User;

import java.time.LocalDate;
import java.time.LocalTime;

public class Booking {
    @ManyToOne
    User user_id;
    LocalDate date;
    LocalTime startTime;
    LocalTime endTime;
    Integer slotCount;
    Status status;
    date
            start_time
    end_time
    slot_count          --1yoki 2
    status              --ACTIVE |
    CANCELLED
            created_at


}
