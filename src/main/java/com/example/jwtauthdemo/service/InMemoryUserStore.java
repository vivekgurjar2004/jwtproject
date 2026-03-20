package com.example.jwtauthdemo.service;

import com.example.jwtauthdemo.model.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class InMemoryUserStore implements UserDetailsService {

    // Keyed by username. This is an in-memory replacement for a database.
    private final Map<String, AppUser> users = new ConcurrentHashMap<>();
    private final PasswordEncoder passwordEncoder;

    public AppUser register(String username, String email, String password) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("username is required");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("email is required");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("password is required");
        }

        String normalizedUsername = username.trim();

        if (users.containsKey(normalizedUsername)) {
            throw new IllegalArgumentException("username already exists");
        }

        String passwordHash = passwordEncoder.encode(password);
        AppUser newUser = new AppUser(
                normalizedUsername,
                email.trim(),
                passwordHash,
                Set.of("USER")
        );

        users.put(normalizedUsername, newUser);
        return newUser;
    }

    public AppUser authenticate(String username, String password) {
        if (username == null || password == null) {
            throw new IllegalArgumentException("username and password are required");
        }

        AppUser user = users.get(username.trim());
        if (user == null || !passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new IllegalArgumentException("invalid username or password");
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = users.get(username.trim());
        if (user == null) {
            throw new UsernameNotFoundException("user not found: " + username);
        }
        return user;
    }
}

