package com.noefer.pontoeletronicoapi.service;

import com.noefer.pontoeletronicoapi.model.UserProfile;
import com.noefer.pontoeletronicoapi.model.WorkDay;
import com.noefer.pontoeletronicoapi.model.dto.WorkDayInfo;
import com.noefer.pontoeletronicoapi.model.dto.WorkDayRequest;
import com.noefer.pontoeletronicoapi.model.dto.WorkDayResponse;
import com.noefer.pontoeletronicoapi.repository.WorkDayRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

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
        return repository.findById(id).orElseThrow(() -> new RuntimeException("WorkDay not found"));
    }

    public WorkDayInfo getWorkDayInfo(Long id) {
        WorkDay workDay = findWorkDay(id);

        return null;
    }
}
