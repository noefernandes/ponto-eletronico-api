package com.noefer.pontoeletronicoapi.service;

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
    public WorkDayResponse register(WorkDayRequest request) {
        WorkDay workDay = new WorkDay(request);
        UserProfile user = userProfileService.findUserById(request.getUserProfileId());
        workDay.setUser(user);
        workDay = repository.save(workDay);
        return new WorkDayResponse(workDay);
    }

    public List<WorkDayResponse> findAllByUserId(Long userId) {
        Long id = userProfileService.findUserById(userId).getId();
        List<WorkDay> workDays = repository.findAllByUserId(id);

        return workDays.stream().map(WorkDayResponse::new).toList();
    }

    public WorkDay findWorkDay(Long id) {
        Optional<WorkDay> workDayOp = repository.findById(id);
        if(workDayOp.isEmpty()) {
            throw new RuntimeException("WorkDay not found");
        }

        return workDayOp.get();
    }

    public WorkDayReport findWorkDayResponse(Long id) {
        Optional<WorkDay> workDayOp = repository.findById(id);
        if(workDayOp.isEmpty()) {
            throw new RuntimeException("WorkDay not found");
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

        return getWorkDayResponse(workDay, targetWorkDuration, totalWorkTime);
    }

    private static WorkDayReport getWorkDayResponse(WorkDay workDay, Duration targetWorkDuration, Duration totalWorkTime) {
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
