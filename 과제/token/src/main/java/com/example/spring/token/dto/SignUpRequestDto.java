package com.example.spring.token.dto;

import com.example.spring.token.domain.entity.User;
import lombok.Getter;

@Getter
public class SignUpRequestDto {

    private String userId;
    private String password;
    private String userName;

    public User toUser(String encodedPassword) {
        return User.builder()
                .userId(userId)
                .password(encodedPassword)
                .name(userName)
                .build();
    }

}
