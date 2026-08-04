package com.example.spring.oauth2.config.oauth2;

public enum AuthProvider {
    LOCAL,
    KAKAO;


    // registrationId("kakao") -> AuthProvider.KAKAO"
    public static AuthProvider from(String registrationId) {
        return AuthProvider.valueOf(registrationId.toUpperCase());
    }

}
