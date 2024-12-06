package com.noefer.pontoeletronicoapi.service;

import com.noefer.pontoeletronicoapi.exception.UserNotFoundException;
import com.noefer.pontoeletronicoapi.exception.WorkDayNotFoundException;
import com.noefer.pontoeletronicoapi.model.*;
import com.noefer.pontoeletronicoapi.model.dto.WorkDayReport;
import com.noefer.pontoeletronicoapi.model.dto.WorkDayRequest;
import com.noefer.pontoeletronicoapi.model.dto.WorkDayResponse;
import com.noefer.pontoeletronicoapi.repository.WorkDayRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class WorkDayService {
    private final WorkDayRepository repository;
    private final UserProfileService userProfileService;

    public WorkDayService(WorkDayRepository repository, UserProfileService userProfileService) {
        this.repository = repository;
        this.userProfileService = userProfileService;
    }

    @Transactional
    public WorkDayReport register(WorkDayRequest request) {
        WorkDay workDay = new WorkDay(request);
        UserProfile user = userProfileService.findUserById(request.getUserProfileId());
        workDay.setUser(user);
        workDay = repository.save(workDay);
        return generateWorkDayReport(workDay, Duration.ofHours(user.getWorkLoad().getHours()));
    }

    public List<WorkDay> findAllByUserId(Long userId) {
        Long id = userProfileService.findUserById(userId).getId();

        if (id == null) {
            throw new UserNotFoundException("Usuário não encontrado");
        }

        return repository.findAllByUserId(id);
    }

    public List<WorkDayResponse> findAllByUserIdResponse(Long userId) {

        List<WorkDay> workDays = findAllByUserId(userId);

        return workDays.stream().map(WorkDayResponse::new).toList();
    }

    public WorkDay findWorkDay(Long id) {
        Optional<WorkDay> workDayOp = repository.findById(id);
        if(workDayOp.isEmpty()) {
            throw new WorkDayNotFoundException("Informações da jornada de trabalho não encontradas");
        }

        return workDayOp.get();
    }

    public WorkDayReport findWorkDayResponse(Long userId) {
        Long id = userProfileService.findUserById(userId).getId();

        List<WorkDay> workDays = this.findAllByUserId(id);

        Optional<WorkDay> workDayOp = workDays.stream().filter(
                workDay -> {
                    System.out.println(workDay.getTimestamp().toLocalDate().toString());
                    System.out.println(LocalDateTime.now().toLocalDate().toString());
                    return workDay.getTimestamp().toLocalDate().equals(LocalDateTime.now().toLocalDate()) &&
                            workDay.getTimestamp().getYear() == LocalDateTime.now().getYear();
                }
        ).findFirst();

        if(workDayOp.isEmpty()) {
            throw new WorkDayNotFoundException("Informações da jornada de trabalho não encontradas");
        }

        Duration targetWorkDuration;
        WorkDay workDay = workDayOp.get();

        if (Objects.requireNonNull(workDay.getUser().getWorkLoad()) == WorkLoad.TESTWORKLOAD) {
            targetWorkDuration = Duration.ofSeconds(30);
        } else {
            targetWorkDuration = Duration.ofHours(workDay.getUser().getWorkLoad().getHours());
        }

        return generateWorkDayReport(workDay, targetWorkDuration);
    }

    public WorkDayReport generateWorkDayReport(WorkDay workDay, Duration targetWorkDuration) {
        List<TimeRecord> timeRecords = workDay.getTimeRecords();

        timeRecords.sort(Comparator.comparing(TimeRecord::getTimestamp));

        LocalDateTime start = null;
        Duration totalWorkTime = Duration.ZERO;

        for (TimeRecord timeRecord : timeRecords) {
            if (timeRecord.getType() == TimeRecordType.CHECKIN || timeRecord.getType() == TimeRecordType.RESUME) {
                start = timeRecord.getTimestamp();
            } else if ((timeRecord.getType() == TimeRecordType.BREAK || timeRecord.getType() == TimeRecordType.CHECKOUT) && start != null) {
                totalWorkTime = totalWorkTime.plus(Duration.between(start, timeRecord.getTimestamp()));
                start = null;
            }
        }

        return getWorkDayStats(workDay, targetWorkDuration, totalWorkTime);
    }

    private static WorkDayReport getWorkDayStats(WorkDay workDay, Duration targetWorkDuration, Duration totalWorkTime) {
        long totalWorkSeconds = totalWorkTime.getSeconds();
        long targetWorkSeconds = targetWorkDuration.getSeconds();

        long remainingSeconds = Math.max(0, targetWorkSeconds - totalWorkSeconds);
        long extraSeconds = Math.max(0, totalWorkSeconds - targetWorkSeconds);

        WorkDayReport workDayReport = new WorkDayReport(workDay);
        workDayReport.setWorkedTime(totalWorkSeconds);
        workDayReport.setRemainingTime(remainingSeconds);
        workDayReport.setExceededTime(extraSeconds);
        return workDayReport;
    }
}
