package uz.anvarovich.barber_personal_website_api.services.system_setting_service;

import org.springframework.transaction.annotation.Transactional;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.SystemSettingDto;

public interface SystemSettingService {
    @Transactional
    void updateSystemSetting(SystemSettingDto systemSettingDto);

    SystemSettingDto getCurrent();

}
