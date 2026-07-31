package com.example.spring.boardwithtoken.service;

import com.example.spring.boardwithtoken.config.jwt.JwtProperties;
import com.example.spring.boardwithtoken.config.jwt.TokenProvider;
import com.example.spring.boardwithtoken.config.jwt.TokenStatus;
import com.example.spring.boardwithtoken.domain.entity.Member;
import com.example.spring.boardwithtoken.dto.RefreshTokenResponseDto;
import com.example.spring.boardwithtoken.util.CookieUtil;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final TokenProvider tokenProvider;
    private final JwtProperties jwtProperties;

    public record TokenPair(String accessToken, String refreshToken) {}

    public TokenPair issueToken(Member member) {
        String accessToken = tokenProvider.generateToken(member, jwtProperties.getAccessTokenValidity());
        String refreshToken = tokenProvider.generateToken(member, jwtProperties.getRefreshTokenValidity());

        return new TokenPair(accessToken, refreshToken);
    }

    public RefreshTokenResponseDto refreshToken(Cookie[] cookies) {
        String refreshToken = getRefreshToken(cookies);

        if (refreshToken != null && tokenProvider.validateToken(refreshToken) == TokenStatus.VALID) {
            Member member = tokenProvider.getTokenDetails(refreshToken);

            TokenPair tokenPair = issueToken(member);

            return RefreshTokenResponseDto.builder()
                    .validated(true)
                    .accessToken(tokenPair.accessToken)
                    .refreshToken(tokenPair.refreshToken)
                    .build();
        }

        return RefreshTokenResponseDto.builder()
                .validated(false)
                .build();
    }

    private String getRefreshToken(Cookie[] cookies) {
        if (cookies == null) return null;

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals(CookieUtil.REFRESH_TOKEN_COOKIE)) {
                return cookie.getValue();
            }
        }
        return null;
    }
}
