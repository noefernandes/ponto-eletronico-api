package com.noefer.pontoeletronicoapi.controller;

import com.noefer.pontoeletronicoapi.model.dto.UserProfileRequest;
import com.noefer.pontoeletronicoapi.model.dto.UserProfileResponse;
import com.noefer.pontoeletronicoapi.service.UserProfileService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserProfileController {

    private final UserProfileService service;

    public UserProfileController(UserProfileService service) {
        this.service = service;
    }

    @PostMapping
    public UserProfileResponse register(@RequestBody UserProfileRequest request) {
        return service.register(request);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    @GetMapping
    public List<UserProfileResponse> findAll() {
        return service.findAllResponse();
    }

    @GetMapping("/{id}")
    public UserProfileResponse findById(@PathVariable Long id) {
        return service.findById(id);
    }
}
