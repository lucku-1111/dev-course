package com.example.spring.webservice.service;

import com.example.spring.webservice.client.AuthClient;
import com.example.spring.webservice.dto.SignUpRequestDto;
import com.example.spring.webservice.dto.SignUpResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthClient authClient;

    public SignUpResponseDto signUp(SignUpRequestDto signUpRequestDto) {
        return authClient.join(signUpRequestDto);
    }
}
