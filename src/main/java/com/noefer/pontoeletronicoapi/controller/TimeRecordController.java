package com.noefer.pontoeletronicoapi.controller;

import com.noefer.pontoeletronicoapi.model.dto.TimeRecordRequest;
import com.noefer.pontoeletronicoapi.model.dto.TimeRecordResponse;
import com.noefer.pontoeletronicoapi.service.TimeRecordService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/time-record")
public class TimeRecordController {
    private final TimeRecordService service;

    public TimeRecordController(TimeRecordService service) {
        this.service = service;
    }

    @PostMapping
    public TimeRecordResponse register(@RequestBody TimeRecordRequest request) {
        return service.register(request);
    }
}
