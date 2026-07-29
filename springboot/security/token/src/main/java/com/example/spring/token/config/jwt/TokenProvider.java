package com.example.spring.token.config.jwt;

import com.example.spring.token.domain.entity.User;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.util.Base64;
import java.util.Date;

// 토큰 생성/검증/해석을 전담하는 컴포넌트
// - generateToken: User 정보를 클레임에 담아 서명된 JWT 문자열 생성
// - validateToken: 서명/만료 검증 결과를 TokenStatus로 반환
// - getTokenDetails: 클레임을 도메인 User로 복원(DB 조회 없이 토큰만으로)
// - getAuthentication: 복원된 User를 시큐리티가 이해하는 Authentication으로 변환

// 서명 키(secretKey)는 서버만 알고 있다.
// 따라서 "서명이 유효하다" = "이 서버가 발급했고 위조되지 않았다"가 성립하고,
// 이것이 세션 없이도 사용자를 신뢰할 수 있는 근거다.

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenProvider {

    private static final String CLAIM_ID = "id";
    private static final String CLAIM_NAME = "name";
    private static final String CLAIM_ROLE = "role";

    private final JwtProperties jwtProperties;

    private SecretKey secretKey;
    private JwtParser jwtParser;

    @PostConstruct
    public void init() {
        // 키와 파서는 불변이므로 요청마다 새로 만들지 않고 한 번만 생성해 재사용한다.
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(jwtProperties.getSecretKey()));
        this.jwtParser = Jwts.parser().verifyWith(secretKey).build();
    }

    public String generateToken(User user, Duration expiredAt) {
        Date now = new Date();
        return makeToken(
                user,
                new Date(now.getTime() + expiredAt.toMillis())
        );
    }

    private String makeToken(User user, Date expire) {
        return Jwts.builder()
                .header().type("JWT").and()
                .issuer(jwtProperties.getIssuer())
                .issuedAt(new Date())
                .expiration(expire)
                .subject(user.getUserId())
                .claim(CLAIM_ID, user.getId())
                .claim(CLAIM_NAME, user.getName())
                .claim(CLAIM_ROLE, user.getRole())
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }
}
