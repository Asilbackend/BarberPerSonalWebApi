package uz.anvarovich.barber_personal_website_api.services.app.admin_block;

import uz.anvarovich.barber_personal_website_api.dto.AdminBlockDto;

import java.time.LocalDate;

public interface AdminBlockCServiceApp {
    void addAdditionalBlock(AdminBlockDto adminBlockDto);

    void cancelBlock(LocalDate date);
}
