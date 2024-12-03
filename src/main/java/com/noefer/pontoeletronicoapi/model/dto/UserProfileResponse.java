package com.noefer.pontoeletronicoapi.model.dto;

import com.noefer.pontoeletronicoapi.model.Role;
import com.noefer.pontoeletronicoapi.model.UserProfile;
import com.noefer.pontoeletronicoapi.model.WorkLoad;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileResponse {
    private Long id;
    private String name;
    private String username;
    private Role role;
    private WorkLoad workLoad;

    public UserProfileResponse(UserProfile userProfile) {
        this.id = userProfile.getId();
        this.name = userProfile.getName();
        this.username = userProfile.getUsername();
        this.role = userProfile.getRole();
        this.workLoad = userProfile.getWorkLoad();
    }
}
