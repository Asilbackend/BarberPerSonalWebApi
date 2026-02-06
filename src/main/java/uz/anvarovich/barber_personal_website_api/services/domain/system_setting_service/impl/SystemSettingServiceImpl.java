package uz.anvarovich.barber_personal_website_api.services.domain.system_setting_service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.SystemSettingDto;
import uz.anvarovich.barber_personal_website_api.entity.SystemSettings;
import uz.anvarovich.barber_personal_website_api.mapper.SystemSettingMapper;
import uz.anvarovich.barber_personal_website_api.repository.SystemSettingsRepository;
import uz.anvarovich.barber_personal_website_api.services.domain.system_setting_service.SystemSettingService;
import uz.anvarovich.barber_personal_website_api.validator.SystemSettingValidator;

@Service
@RequiredArgsConstructor
public class SystemSettingServiceImpl implements SystemSettingService {
    private final SystemSettingsRepository systemSettingsRepository;

    @Override
    @Transactional
    public void updateSystemSetting(SystemSettingDto systemSettingDto) {
        systemSettingsRepository.deleteAll();
        SystemSettingValidator.validate(systemSettingDto);
        SystemSettings entity = SystemSettingMapper.toEntity(systemSettingDto);
        systemSettingsRepository.save(entity);
    }

    @Override
    public SystemSettingDto getCurrent() {
        return systemSettingsRepository.findTopByOrderByIdDesc()
                .map(SystemSettingMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("System settings topilmadi"));
    }


    /*public SystemSettingDto getOld() {
        List<SystemSettings> all = systemSettingsRepository.findAll();
        if (all.size() >= 2) {
            SystemSettings systemSettings = all.get(all.size() - 2);
            return SystemSettingMapper.toDto(systemSettings);
        }
        SystemSettings one = all.getFirst();
        return SystemSettingMapper.toDto(one);
    }*/
/*
    @Transactional
    public void updateAllRelatedPlansAfterSettingsChange() {
        SystemSettingDto current = getCurrent();
        SystemSettingDto previous = getOld();  // oldingi qiymatlarni saqlab qolgan bo'lishingiz kerak
        if (!hasRelevantChanges(current, previous)) {
            return;
        }
        LocalDate today = LocalDate.now();
        LocalDate weekStart = today.with(DayOfWeek.MONDAY); // joriy hafta boshlanishi
        List<LocalDate> bookedDates = bookingRepository.findAllWithWeekStartAfterOrEqual(weekStart);
        List<DailyPlan> dailyPlans = dailyPlanService.findAllWithWeekStartAfterOrEqual(weekStart);
        for (DailyPlan dailyPlan : dailyPlans) {
            if (bookedDates.contains(dailyPlan.getDate())) {
                markOutOfSlots(dailyPlan, current);
            }
            updateDailyPlanAndSlots(dailyPlan, current);
        }
    }


    private boolean hasRelevantChanges(SystemSettingDto curr, SystemSettingDto prev) {
        return !Objects.equals(curr.defaultWorkStartTime(), prev.defaultWorkStartTime()) ||
                !Objects.equals(curr.defaultWorkEndTime(), prev.defaultWorkStartTime()) ||
                !Objects.equals(curr.defaultSlotDurationMin(), prev.defaultSlotDurationMin());
    }

    private void updateDailyPlanAndSlots(DailyPlan dailyPlan, SystemSettingDto settings) {
        if (Boolean.TRUE.equals(dailyPlan.getIsDayOff())) {
            return;
        }
        // Eski slotlarni o'chirish (faqat OPEN bo'lganlarni!)
        timeSlotService.deleteAllOpenSlotsByDailyPlanId(dailyPlan.getId());
        // Yangi ish vaqti va slot uzunligini o'rnatish
        dailyPlan.setWorkStartTime(settings.defaultWorkStartTime());
        dailyPlan.setWorkEndTime(settings.defaultWorkEndTime());
        dailyPlan.setSlotDurationMin(settings.defaultSlotDurationMin());

        dailyPlanService.save(dailyPlan); // yangilangan dailyPlan saqlanadi

        // Yangi slotlarni yaratish
        createTimeSlotsForDailyPlan(dailyPlan);
    }


    *//**
     * Booking bo'lgan kunlarda faqat isOutsideSchedule ni belgilaymiz
     *//*
    // boshqa threadda ham ishlasa boladi
    private void markOutOfSlots(DailyPlan dailyPlan, SystemSettingDto settings) {
        List<TimeSlot> slots = timeSlotService.findAllByDailyPlanId(dailyPlan.getId());
        LocalTime newStart = settings.defaultWorkStartTime();
        LocalTime newEnd = settings.defaultWorkEndTime();
        int newDuration = settings.defaultSlotDurationMin();
        for (TimeSlot slot : slots) {
            // Agar slot ish vaqtiga to'g'ri kelmay qolsa
            boolean isOutside = isSlotOutsideNewSchedule(
                    slot.getStartTime(),
                    slot.getEndTime(),
                    newStart,
                    newEnd,
                    newDuration
            );
            if (isOutside && !Boolean.TRUE.equals(slot.getIsOutsideSchedule())) {
                slot.setIsOutsideSchedule(true);
                timeSlotRepository.save(slot);
            }
        }
    }


    private boolean isSlotOutsideNewSchedule(
            LocalTime slotStart,
            LocalTime slotEnd,
            LocalTime newWorkStart,
            LocalTime newWorkEnd,
            int newSlotDuration
    ) {
        // Yangi ish vaqtidan oldin yoki keyin bo'lsa
        if (slotEnd.isBefore(newWorkStart) || slotStart.isAfter(newWorkEnd)) {
            return true;
        }

        // Davomiylik mos kelmasa (masalan 30 min edi, endi 45 min)
        Duration duration = Duration.between(slotStart, slotEnd);
        return duration.toMinutes() != newSlotDuration;
    }

    private void createTimeSlotsForDailyPlan(DailyPlan dailyPlan) {

        LocalTime current = dailyPlan.getWorkStartTime();
        LocalTime end = dailyPlan.getWorkEndTime();
        int durationMinutes = dailyPlan.getSlotDurationMin();

        while (current.plusMinutes(durationMinutes).isBefore(end) ||
                current.plusMinutes(durationMinutes).equals(end)) {

            TimeSlot slot = TimeSlot.builder()
                    .dailyPlan(dailyPlan)
                    .startTime(current)
                    .endTime(current.plusMinutes(durationMinutes))
                    .slotStatus(SlotStatus.OPEN)
                    .isOutsideSchedule(false)
                    .build();

            timeSlotRepository.save(slot);

            current = current.plusMinutes(durationMinutes);
        }
    }*/
}
