package com.example.spring.token.config.jwt;

public enum TokenStatus {
    VALID, // 유효
    EXPIRED, // 만료
    INVALID // 서명 불일치, 형식 오류 등
}
