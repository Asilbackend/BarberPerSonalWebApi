package uz.anvarovich.barber_personal_website_api.services.domain.system_setting_service;

import org.springframework.transaction.annotation.Transactional;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.SystemSettingDto;

public interface SystemSettingService {
    void updateSystemSetting(SystemSettingDto systemSettingDto);

    SystemSettingDto getCurrent();
}
