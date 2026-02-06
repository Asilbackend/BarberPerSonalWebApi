package uz.anvarovich.barber_personal_website_api.services.domain.time_slote.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.BookDto;
import uz.anvarovich.barber_personal_website_api.dto.req_dto.UpdateDailyPlanDto;
import uz.anvarovich.barber_personal_website_api.entity.DailyPlan;
import uz.anvarovich.barber_personal_website_api.entity.enums.SlotStatus;
import uz.anvarovich.barber_personal_website_api.entity.time_slot.TimeSlot;
import uz.anvarovich.barber_personal_website_api.projection.TimeSlotProjection;
import uz.anvarovich.barber_personal_website_api.repository.TimeSlotRepository;
import uz.anvarovich.barber_personal_website_api.services.domain.system_setting_service.SystemSettingService;
import uz.anvarovich.barber_personal_website_api.services.domain.time_slote.TimeSlotService;
import uz.anvarovich.barber_personal_website_api.validator.BookingValidator;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TimeSlotServiceImpl implements TimeSlotService {

    private final TimeSlotRepository timeSlotRepository;
    private final SystemSettingService systemSettingService;

    @Override
    public List<TimeSlotProjection> findByDaily(LocalDate date) {
        return timeSlotRepository.findByDaily(date);
    }


    @Override
    public List<TimeSlot> findByDailyPlanId(Long dailyPlanId) {
        return timeSlotRepository.findByDailyPlanId(dailyPlanId);
    }

    @Override
    @Transactional
    public List<TimeSlot> createTimeSlots(DailyPlan dailyPlan, UpdateDailyPlanDto currentSetting) {
        LocalTime workStartTime = currentSetting.workStartTime();
        LocalTime workEndTime = currentSetting.workEndTime();
        Integer slotDurationMinutes = currentSetting.slotDurationMin();
        if (workStartTime == null || workEndTime == null || slotDurationMinutes == null || slotDurationMinutes <= 0) {
            // log yozish yoki exception tashlash mumkin
            throw new IllegalArgumentException("validation error : workStartTime == null || workEndTime == null || slotDurationMinutes == null || slotDurationMinutes <= 0");
        }

        List<TimeSlot> slots = new ArrayList<>();
        Duration slotDuration = Duration.ofMinutes(slotDurationMinutes);
        LocalTime current = workStartTime;
        while (true) {
            LocalTime slotEnd = current.plus(slotDuration);

            if (slotEnd.isAfter(workEndTime)) {
                break;
            }

            // Slotni qo'shamiz (current hozirgi boshlanish vaqti)
            slots.add(new TimeSlot(
                    dailyPlan,
                    current,
                    slotEnd,
                    SlotStatus.OPEN,
                    false
            ));

            // Keyingi slot uchun current ni yangilaymiz
            current = slotEnd;
        }

        // Agar hech qanday slot yaratilmasa (masalan duration juda katta bo'lsa)
        if (slots.isEmpty()) {
            throw new EntityNotFoundException("Slotlar yaratilmadi");
            // log yozish mumkin: "No slots created for dailyPlan " + dailyPlan.getId()
        }
        return timeSlotRepository.saveAll(slots);
    }

    @Override
    public List<TimeSlot> findAllByIdsAndDate(List<Long> longs, LocalDate date) {
        List<TimeSlot> allByIdsAndDate = timeSlotRepository.findAllByIdsAndDate(longs, date);
        if (allByIdsAndDate.size() != longs.size()) {
            throw new IllegalArgumentException("xato kunlar kirtildi,  (dam olish kuni yoki timeslot mavjud bolmagan kun)");
        }
        return timeSlotRepository.findAllByIdsAndDate(longs, date);
    }

    @Override
    public List<TimeSlot> findAllByDailyPlanId(Long id) {
        return timeSlotRepository.findAllByDailyPlanId(id);
    }

    @Override
    public void deleteAllOpenSlotsByDailyPlanId(Long id) {
        timeSlotRepository.deleteAllOpenByDailyPlanId(id);
    }

    @Override
    @Transactional
    public void createTimeSlotsByOld(Map<DailyPlan, DailyPlan> oldAndNew) {
        List<TimeSlot> unsavedTimeSlot = new ArrayList<>();
        for (DailyPlan oldDailyPlan : oldAndNew.keySet()) {
            DailyPlan newDailyPlan = oldAndNew.get(oldDailyPlan);
            List<TimeSlotProjection> oldSlots = timeSlotRepository.findByDaily(oldDailyPlan.getDate());
            for (TimeSlotProjection oldSlot : oldSlots) {
                TimeSlot timeSlot = new TimeSlot(newDailyPlan, oldSlot.getStartTime(), oldSlot.getEndTime(), oldSlot.getSlotStatus(), oldSlot.getIsOutsideSchedule());
                unsavedTimeSlot.add(timeSlot);
            }
        }
        timeSlotRepository.saveAll(unsavedTimeSlot);
    }

    @Override
    @Transactional
    public void updateByDailyPlanId(DailyPlan dailyPlan, UpdateDailyPlanDto updateDailyPlanDto) {
        deleteAllOpenSlotsByDailyPlanId(dailyPlan.getId());
        List<TimeSlot> oldTimeSlots = findByDailyPlanId(dailyPlan.getId());
        List<TimeSlot> timeSlotsNew = createTimeSlots(dailyPlan, updateDailyPlanDto);
        for (TimeSlot timeSlot : oldTimeSlots) {
            if (!timeSlot.getSlotStatus().equals(SlotStatus.OPEN)) {
                // agar hech bir yangi slot bilan to'liq mos kelmasa
                if (timeSlotsNew.stream().noneMatch(newSlot ->
                        newSlot.getStartTime().equals(timeSlot.getStartTime()) &&
                                newSlot.getEndTime().equals(timeSlot.getEndTime()))) {
                    timeSlot.setIsOutsideSchedule(true);
                    timeSlotRepository.save(timeSlot);
                }
            }
        }
    }

    @Override
    @Transactional
    public void blockByDailyPlanId(Long dailyPlanId) {
        List<TimeSlot> unsaved = new ArrayList<>();
        for (TimeSlot timeSlot : findByDailyPlanId(dailyPlanId)) {
            timeSlot.setSlotStatus(SlotStatus.BLOCKED);
            unsaved.add(timeSlot);
        }
        timeSlotRepository.saveAll(unsaved);
    }

    @Override
    @Transactional
    public void cancelBlockByDailyPlanId(Long dailyPlanId) {
        List<TimeSlot> unsaved = new ArrayList<>();
        for (TimeSlot timeSlot : findByDailyPlanId(dailyPlanId)) {
            timeSlot.setSlotStatus(SlotStatus.OPEN);
            unsaved.add(timeSlot);
        }
        timeSlotRepository.saveAll(unsaved);
    }

    @Override
    @Transactional
    public List<TimeSlot> book(BookDto dto, boolean isAdmin) {
        List<Long> timeSlotIdss = dto.timeSlotsId();
        LocalDate date = dto.date();
        List<TimeSlot> timeSlots = findAllByIdsAndDate(timeSlotIdss, date);
        BookingValidator.validate(date, timeSlots, isAdmin, systemSettingService.getCurrent());
        List<TimeSlot> unsaved = new ArrayList<>();
        for (TimeSlot timeSlot : timeSlots) {
            if (isAdmin) {
                timeSlot.setSlotStatus(SlotStatus.BLOCKED);
            } else {
                timeSlot.setSlotStatus(SlotStatus.BOOKED);
            }
            unsaved.add(timeSlot);
        }
        return timeSlotRepository.saveAll(unsaved);
    }

    @Override
    @Transactional
    public void cancelBookByAdmin(BookDto bookDto) {
        LocalDate date = bookDto.date();
        List<Long> timeSlotIdss = bookDto.timeSlotsId();
        List<TimeSlot> unsaved = new ArrayList<>();
        for (TimeSlot timeSlot : findAllByIdsAndDate(timeSlotIdss, date)) {
            if (timeSlot.getSlotStatus().equals(SlotStatus.BLOCKED)) {
                timeSlot.setSlotStatus(SlotStatus.OPEN);
                unsaved.add(timeSlot);
            }
        }
        timeSlotRepository.saveAll(unsaved);
    }

    @Override
    @Transactional
    public void cancelSlotsByUser(List<TimeSlot> timeSlots) {
        List<TimeSlot> unsaved = new ArrayList<>();
        for (TimeSlot timeSlot : timeSlots) {
            timeSlot.setSlotStatus(SlotStatus.OPEN);
            unsaved.add(timeSlot);
        }
        timeSlotRepository.saveAll(unsaved);
    }

    @Override
    @Transactional
    public void deleteAllOutsideTrue(List<TimeSlot> timeSlotsByBookingId) {
        List<TimeSlot> undDeletedSlots = new ArrayList<>();
        for (TimeSlot timeSlot : timeSlotsByBookingId) {
            if (timeSlot.getIsOutsideSchedule()) {
                undDeletedSlots.add(timeSlot);
            }
        }
        timeSlotRepository.deleteAll(undDeletedSlots);
    }
}
