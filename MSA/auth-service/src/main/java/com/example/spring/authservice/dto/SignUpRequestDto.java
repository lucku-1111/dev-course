package com.example.spring.authservice.dto;

import com.example.spring.authservice.domain.entity.Role;
import com.example.spring.authservice.domain.entity.User;
import lombok.Getter;
import lombok.ToString;

@Getter
public class SignUpRequestDto {

    private String userId;
    private String password;
    private String userName;
    private Role role;

    public User toUser(String encodedPassword) {
        return User.builder()
                .userId(userId)
                .password(encodedPassword)
                .name(userName)
                .role(role != null ? role : Role.ROLE_USER)
                .build();
    }

}
