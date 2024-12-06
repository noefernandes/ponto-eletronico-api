package com.noefer.pontoeletronicoapi.controller;

import com.noefer.pontoeletronicoapi.model.dto.TimeRecordRequest;
import com.noefer.pontoeletronicoapi.model.dto.TimeRecordResponse;
import com.noefer.pontoeletronicoapi.service.TimeRecordService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("https://ponto-eletronico-hkj3.onrender.com/")
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
