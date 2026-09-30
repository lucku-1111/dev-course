package com.example.spring.authservice.dto;

import com.example.spring.authservice.domain.entity.Role;
import lombok.Getter;

@Getter
public class OAuthSignUpRequestDto {
    private String signupToken;
    private Role role;
}
