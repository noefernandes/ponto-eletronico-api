package com.noefer.pontoeletronicoapi.service;

import com.noefer.pontoeletronicoapi.model.TimeRecord;
import com.noefer.pontoeletronicoapi.model.WorkDay;
import com.noefer.pontoeletronicoapi.model.dto.TimeRecordRequest;
import com.noefer.pontoeletronicoapi.model.dto.TimeRecordResponse;
import com.noefer.pontoeletronicoapi.repository.TimeRecordRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class TimeRecordService {
    private final TimeRecordRepository repository;
    private final WorkDayService workDayService;

    public TimeRecordService(TimeRecordRepository repository, WorkDayService workDayService) {
        this.repository = repository;
        this.workDayService = workDayService;
    }

    @Transactional
    public TimeRecordResponse register(TimeRecordRequest request) {
        Long workDayId = request.getWorkDayId();
        TimeRecord timeRecord = new TimeRecord(request);

        WorkDay workDay = workDayService.findWorkDay(workDayId);
        timeRecord.setWorkDay(workDay);
        workDay.getTimeRecords().add(timeRecord);

        timeRecord = repository.save(timeRecord);
        return new TimeRecordResponse(timeRecord);
    }
}
