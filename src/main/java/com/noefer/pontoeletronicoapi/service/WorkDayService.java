package com.noefer.pontoeletronicoapi.service;

import com.noefer.pontoeletronicoapi.model.UserProfile;
import com.noefer.pontoeletronicoapi.model.WorkDay;
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
        UserProfile user = userProfileService.findByIdUser(request.getUserProfileId());
        workDay.setUser(user);
        workDay = repository.save(workDay);
        return new WorkDayResponse(workDay);
    }

    public List<WorkDay> findAllByWorkDay(Long id) {
        return repository.findAllById(List.of(id));
    }
}
