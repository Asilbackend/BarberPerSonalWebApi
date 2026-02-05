package uz.anvarovich.barber_personal_website_api.services.domain.dailyPlan_service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.SystemSettingDto;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.UpdateDailyPlanDto;
import uz.anvarovich.barber_personal_website_api.entity.DailyPlan;
import uz.anvarovich.barber_personal_website_api.entity.WeeklyPlan;
import uz.anvarovich.barber_personal_website_api.repository.DailyPlanRepository;
import uz.anvarovich.barber_personal_website_api.services.domain.dailyPlan_service.DailyPlanService;
import uz.anvarovich.barber_personal_website_api.validator.DailyPlanValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DailyPlanServiceImpl implements DailyPlanService {

    private final DailyPlanRepository dailyPlanRepository;

    @Override
    @Transactional
    public DailyPlan cerateDailyPlan(LocalDate toDay, SystemSettingDto currentSetting, WeeklyPlan weeklyPlan) {
        DailyPlan dailyPlan = new DailyPlan(
                toDay,
                currentSetting.defaultWorkStartTime(),
                currentSetting.defaultWorkStartTime().plusMinutes(currentSetting.defaultSlotDurationMin()),
                currentSetting.defaultSlotDurationMin(),
                false,
                weeklyPlan
        );
        return dailyPlanRepository.save(dailyPlan);
    }

    @Override
    @Transactional
    public Map<DailyPlan, DailyPlan> createDailyPlansByOld(List<DailyPlan> oldDailyPlans, WeeklyPlan newWeeklyPlan) {
        List<DailyPlan> dailyPlans = new ArrayList<>();
        Map<DailyPlan, DailyPlan> oldAndNew = new HashMap<>();
        for (DailyPlan oldDailyPlan : oldDailyPlans) {
            LocalDate date = oldDailyPlan.getDate().plusWeeks(1);
            DailyPlan clone = oldDailyPlan.clone();
            DailyPlan dailyPlan = new DailyPlan(date, clone.getWorkStartTime(), clone.getWorkEndTime(), clone.getSlotDurationMin(), clone.getIsDayOff(), newWeeklyPlan);
            dailyPlans.add(dailyPlan);
            oldAndNew.put(oldDailyPlan, dailyPlan);
        }
        dailyPlanRepository.saveAll(dailyPlans);
        return oldAndNew;
    }

    @Override
    @Transactional
    public DailyPlan update(LocalDate date, UpdateDailyPlanDto updateDailyPlanDto) {
        DailyPlanValidator.validate(updateDailyPlanDto, date);
        DailyPlan dailyPlan = findByDate(date);
        dailyPlan.setSlotDurationMin(updateDailyPlanDto.slotDurationMin());
        dailyPlan.setWorkStartTime(updateDailyPlanDto.workStartTime());
        dailyPlan.setWorkEndTime(updateDailyPlanDto.workEndTime());
        return dailyPlanRepository.save(dailyPlan);
    }

    @Override
    public boolean setDayOff(DailyPlan dailyPlan) {
        if (dailyPlan.getIsDayOff() != null && dailyPlan.getIsDayOff()) {
            return false;
        } else {
            dailyPlan.setIsDayOff(true);
            dailyPlanRepository.save(dailyPlan);
            return true;
        }
    }

    @Override
    public boolean cancelDayOff(DailyPlan dailyPlan) {
        if (dailyPlan.getIsDayOff() == null || !dailyPlan.getIsDayOff()) {
            return false;
        } else {
            dailyPlan.setIsDayOff(false);
            dailyPlanRepository.save(dailyPlan);
            return true;
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<DailyPlan> findAllByWeeklyPlanId(Long id) {
        return dailyPlanRepository.findAllByWeeklyPlanId(id);
    }


    @Override
    public DailyPlan findByDate(LocalDate date) {
        return dailyPlanRepository.findByDate(date).orElseThrow(() -> new EntityNotFoundException("date boyicha DailyPlan topilmadi"));
    }


}
