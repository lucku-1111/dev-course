package com.example.spring.token.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

// * JWT(JSON Web Token)
// JWT는 당사자 간의 정보를 JSON 객체로 안전하게 전달하기 위한 토큰 표준.
// '."(점)으로 구분된 세 부분으로 구성된다.

// xxxxx.yyyyy.zzzzz -> Header.Payload.Signature

// 1. Header
// - 토큰의 메타 정보 : 서명 알고리즘(alg), 토큰 타입(typ)
// - 예: { "alg" : "HS256", "typ" : "JWT" }
// - 이 JSON을 Base64Url 인코딩한 것이 첫 번째 부분

// 2. Payload
// - 실제 전달할 데이터인 클레임(Claim)들을 담는다.
// - 주의: 암호화가 아니라 '인코딩'일 뿐 -> 누구나 디코딩해서 볼 수 있으므로 비밀번호 등 민감 정보를 절대 넣으면 안 된다.

// 클레임이란?
// - Payload에 담기는 정보 한 조각(key-value 쌍 하나하나)을 클레임이라 한다.
// - 토큰이 "이 사용자는 test다", "이 토큰은 10시에 만료된다" 같은 사실을 '주장(claim)'한다는 의미.
// 서명이 유효하면 그 주장들을 신뢰할 수 있다.

// 클레임의 3가지 종류
// 1) 등록된 클레임(Registered) - RFC 7519 표준에 미리 정의된 이름들
// sub(주체/사용자 식별자), iss(발급자), exp(만료 시간), iat(발급 시각), aud(사용 대상), jti(토큰 고유 ID)
// -> 이름이 3글자로 짧은 이유는 토큰 크기를 줄이기 위해
// 2) 공개 클레임(Public) - 시스템 간 충돌하지 않도록 공개적으로 정의해서 쓰는 것
// 보통 URI 형태로 이름을 짓는다. 직접 만들 일은 드묾
// 3) 비공개 클레임(Private) - 서버-클라이언트 간 약속한 커스텀 데이터
// 예: role, nickname,...

// 3. Signature
// - 토큰이 위조되지 않았음을 증명하는 부분
// - HMACSHA256( base64UrlEncode(header) + "." + base64UrlEncode(payload), secretKey )
// - Payload를 조작하면 서명이 일치하지 않아 검증 단계에서 탐지된다.
// - 즉 JWT는 '내용을 숨기는' 것이 아니라 '변조를 막는' 기술이다.

@Configuration
@EnableWebSecurity
public class SecurityConfig {
}
