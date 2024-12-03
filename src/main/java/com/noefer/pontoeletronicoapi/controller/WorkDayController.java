package com.noefer.pontoeletronicoapi.controller;

import com.noefer.pontoeletronicoapi.model.dto.WorkDayRequest;
import com.noefer.pontoeletronicoapi.model.dto.WorkDayResponse;
import com.noefer.pontoeletronicoapi.service.WorkDayService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workday")
public class WorkDayController {
    private final WorkDayService service;

    public WorkDayController(WorkDayService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<WorkDayResponse> register(@RequestBody WorkDayRequest request) {
        return ResponseEntity.ok(service.register(request));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<WorkDayResponse>> findAllByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(service.findAllByUserId(userId));
    }
}
