package com.example.spring.token.controller;

import com.example.spring.token.dto.SignUpRequestDto;
import com.example.spring.token.dto.SignUpResponseDto;
import com.example.spring.token.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;

    @PostMapping("/join")
    public SignUpResponseDto signUp(@RequestBody SignUpRequestDto request) {
        userService.signUp(request);
        return new SignUpResponseDto("/users/login");
    }
}
