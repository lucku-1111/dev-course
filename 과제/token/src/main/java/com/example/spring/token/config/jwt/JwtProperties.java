package com.example.spring.token.config.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;

@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        String issuer,
        String secretKey,
        Duration accessTokenValidity,
        Duration refreshTokenValidity
) {
}
