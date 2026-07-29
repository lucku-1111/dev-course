package com.example.spring.token.dto;

import com.example.spring.token.domain.entity.Role;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

    private String userId;
    private String password;
    private String username;
    private Role role;

}
