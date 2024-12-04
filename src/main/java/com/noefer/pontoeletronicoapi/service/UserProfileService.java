package com.noefer.pontoeletronicoapi.service;

import com.noefer.pontoeletronicoapi.exception.IncorrectPasswordException;
import com.noefer.pontoeletronicoapi.exception.UserNotFoundException;
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

    public UserProfile findUserById(Long id) {
        return repository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
    }
    
    public UserProfileResponse findById(Long id) {
        UserProfile userProfile = repository.findById(id).orElseThrow(() -> new UserNotFoundException("Usuário não encontrado"));
        return new UserProfileResponse(userProfile);
    }

    public UserProfileResponse findByUsername(String username) {
        UserProfile userProfile = repository.findByUsername(username);
        if(userProfile == null) throw new UserNotFoundException("Usuário não encontrado");
        return new UserProfileResponse(userProfile);
    }

    public UserProfileResponse login(UserProfileRequest request) {
        UserProfile userProfile = repository.findByUsername(request.getUsername());

        if(userProfile == null) throw new UserNotFoundException("Usuário não encontrado");

        if(!userProfile.getPassword().equals(request.getPassword())) {
            throw new IncorrectPasswordException("Senha incorreta");
        }

        return new UserProfileResponse(userProfile);
    }
}
