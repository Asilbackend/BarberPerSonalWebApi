package uz.anvarovich.barber_personal_website_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.anvarovich.barber_personal_website_api.entity.SystemSettings;

import java.util.Optional;

public interface SystemSettingsRepository extends JpaRepository<SystemSettings, Long> {
    Optional<SystemSettings> findTopByOrderByIdDesc();

}