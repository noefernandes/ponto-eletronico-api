package com.noefer.pontoeletronicoapi.service;

import com.noefer.pontoeletronicoapi.repository.TimeRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class TimeRecordService {
    private final TimeRecordRepository repository;

    public TimeRecordService(TimeRecordRepository repository) {
        this.repository = repository;
    }
}
