package com.example.spring.token.service;

import com.example.spring.token.config.jwt.JwtProperties;
import com.example.spring.token.config.jwt.TokenProvider;
import com.example.spring.token.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProvider tokenProvider;
    private final JwtProperties jwtProperties;

    public record TokenPair(String accessToken, String refreshToken) {}

    public TokenPair issueToken(User user) {
        String accessToken = tokenProvider.generateToken(user, jwtProperties.getAccessTokenValidity());
        String refreshToken = tokenProvider.generateToken(user, jwtProperties.getRefreshTokenValidity());

        return new TokenPair(accessToken, refreshToken);
    }
}
