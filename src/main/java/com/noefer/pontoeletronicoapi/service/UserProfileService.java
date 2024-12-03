package com.noefer.pontoeletronicoapi.service;

import com.noefer.pontoeletronicoapi.model.UserProfile;
import com.noefer.pontoeletronicoapi.model.dto.UserProfileRequest;
import com.noefer.pontoeletronicoapi.model.dto.UserProfileResponse;
import com.noefer.pontoeletronicoapi.repository.UserProfileRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileService {
    private final UserProfileRepository repository;

    public UserProfileService(UserProfileRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public UserProfile register(UserProfile userProfile) {
        return repository.save(userProfile);
    }

    public UserProfileResponse register(UserProfileRequest request) {
        UserProfile userProfile = new UserProfile(request);
        userProfile = repository.save(userProfile);
        return new UserProfileResponse(userProfile);
    }

    @Transactional
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }

    public List<UserProfileResponse> findAllResponse() {
        return repository.findAll().stream().map(UserProfileResponse::new).toList();
    }

    public List<UserProfile> findAll() {
        return repository.findAll();
    }

    public UserProfile findByIdUser(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }
    
    public UserProfileResponse findById(Long id) {
        UserProfile userProfile = repository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return new UserProfileResponse(userProfile);
    }
}
