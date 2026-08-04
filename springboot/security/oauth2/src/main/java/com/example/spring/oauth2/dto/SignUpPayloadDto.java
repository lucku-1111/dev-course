package com.example.spring.oauth2.dto;

import com.example.spring.oauth2.config.oauth2.AuthProvider;
import lombok.AllArgsConstructor;

/**
 * @param providerId SNS 회원번호(토큰의 sub 클레임에서 복원)
 */
public record SignUpPayloadDto(AuthProvider provider, String providerId, String email, String name) {
}
