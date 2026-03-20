package com.example.jwtauthdemo.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
    // Must be long enough for HS256 signing (>= 256 bits).
    private String secret;
    private long expirationMs = 3600000;
}

