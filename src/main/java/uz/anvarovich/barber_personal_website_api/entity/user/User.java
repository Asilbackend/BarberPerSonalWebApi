package uz.anvarovich.barber_personal_website_api.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import uz.anvarovich.barber_personal_website_api.entity.abs.BaseEntity;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends BaseEntity {

    private String full_name;
    private String phone;
    role            --ADMIN |
    USER
            created_at

}
