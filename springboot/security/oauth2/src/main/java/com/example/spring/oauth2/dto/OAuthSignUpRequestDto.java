package com.example.spring.oauth2.dto;

import com.example.spring.oauth2.domain.entity.Role;
import lombok.Getter;

@Getter
public class OAuthSignUpRequestDto {
    private String signupToken;
    private Role role;
}
