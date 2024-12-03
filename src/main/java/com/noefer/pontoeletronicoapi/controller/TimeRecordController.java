package com.noefer.pontoeletronicoapi.controller;

import com.noefer.pontoeletronicoapi.service.TimeRecordService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/time-records")
public class TimeRecordController {
    private final TimeRecordService service;

    public TimeRecordController(TimeRecordService service) {
        this.service = service;
    }
}
