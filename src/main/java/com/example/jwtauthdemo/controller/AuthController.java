package com.example.jwtauthdemo.controller;

import com.example.jwtauthdemo.api.AuthResponse;
import com.example.jwtauthdemo.api.LoginRequest;
import com.example.jwtauthdemo.api.RegisterRequest;
import com.example.jwtauthdemo.model.AppUser;
import com.example.jwtauthdemo.security.JwtService;
import com.example.jwtauthdemo.service.InMemoryUserStore;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final InMemoryUserStore userStore;
    private final JwtService jwtService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterRequest request) {
        AppUser created = userStore.register(request.getUsername(), request.getEmail(), request.getPassword());
        return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "registered", "username", created.getUsername()));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        AppUser user = userStore.authenticate(request.getUsername(), request.getPassword());
        String token = jwtService.generateToken(user);
        return ResponseEntity.ok(new AuthResponse(token, "Bearer"));
    }
}

