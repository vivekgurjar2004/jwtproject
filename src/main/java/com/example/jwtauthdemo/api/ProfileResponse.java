package com.example.jwtauthdemo.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileResponse {
    private String username;
    private String email;
    private Set<String> roles;
}

