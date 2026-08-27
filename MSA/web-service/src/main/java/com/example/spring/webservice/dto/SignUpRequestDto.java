package com.example.spring.webservice.dto;

import com.example.spring.webservice.enums.Role;
import lombok.Getter;

@Getter
public class SignUpRequestDto {
    private String userId;
    private String password;
    private String userName;
    private Role role;
}
