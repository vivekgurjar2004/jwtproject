package com.example.jwtauthdemo.controller;

import com.example.jwtauthdemo.api.ProfileResponse;
import com.example.jwtauthdemo.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ProfileController {

    @GetMapping("/profile")
    public ProfileResponse profile(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return new ProfileResponse(null, null, Set.of());
        }

        Object principal = authentication.getPrincipal();
        if (principal instanceof AppUser user) {
            return new ProfileResponse(user.getUsername(), user.getEmail(), user.getRoles());
        }

        return new ProfileResponse(authentication.getName(), null, Set.of());
    }
}

