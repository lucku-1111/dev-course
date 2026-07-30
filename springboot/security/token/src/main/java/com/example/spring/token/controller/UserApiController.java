package com.example.spring.token.controller;

import com.example.spring.token.config.jwt.JwtProperties;
import com.example.spring.token.dto.SignInRequestDto;
import com.example.spring.token.dto.SignInResponseDto;
import com.example.spring.token.dto.SignUpRequestDto;
import com.example.spring.token.dto.SignUpResponseDto;
import com.example.spring.token.service.UserService;
import com.example.spring.token.util.CookieUtil;
import jakarta.servlet.http.HttpServletResponse;
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
    private final JwtProperties jwtProperties;

    @PostMapping("/join")
    public SignUpResponseDto join(@RequestBody SignUpRequestDto request) {
        userService.signUp(request);
        return SignUpResponseDto.builder()
                .url("/users/login")
                .build();
    }

    @PostMapping("/login")
    public SignInResponseDto login(
            @RequestBody SignInRequestDto requestDto,
            HttpServletResponse response
    ) {
        SignInResponseDto signInResponseDto = userService.login(requestDto);

        CookieUtil.addCookie(
                response,
                CookieUtil.REFRESH_TOKEN_COOKIE,
                signInResponseDto.getRefreshToken(),
                (int) jwtProperties.getRefreshTokenValidity().toSeconds()
        );

        signInResponseDto.setAccessToken(null);

        return signInResponseDto;
    }
}
