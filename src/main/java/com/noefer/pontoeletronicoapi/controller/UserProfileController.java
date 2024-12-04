package com.noefer.pontoeletronicoapi.controller;

import com.noefer.pontoeletronicoapi.model.dto.UserProfileRequest;
import com.noefer.pontoeletronicoapi.model.dto.UserProfileResponse;
import com.noefer.pontoeletronicoapi.service.UserProfileService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<UserProfileResponse> register(@RequestBody UserProfileRequest request) {
        return new ResponseEntity<>(service.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<UserProfileResponse> login(@RequestBody UserProfileRequest request) {
        var userProfile = service.login(request);
        if(userProfile != null) {
            return ResponseEntity.ok(userProfile);
        }
        return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserProfileResponse>> findAll() {
        return new ResponseEntity<>(service.findAllResponse(), HttpStatus.OK);
    }

    @GetMapping("/{username}")
    public UserProfileResponse findUserByUsername(@PathVariable("username") String username) {
        return service.findByUsername(username);
    }
}
