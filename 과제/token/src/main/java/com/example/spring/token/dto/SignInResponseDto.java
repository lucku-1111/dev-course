package com.example.spring.token.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SignInResponseDto {

    private boolean isLoggedIn;
    private String url;
    private String userName;
    private String userId;
    private String message;

}
