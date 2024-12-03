package com.noefer.pontoeletronicoapi.model.dto;

import com.noefer.pontoeletronicoapi.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileRequest {
    private String name;
    private String username;
    private String password;
    private Role role;
}
