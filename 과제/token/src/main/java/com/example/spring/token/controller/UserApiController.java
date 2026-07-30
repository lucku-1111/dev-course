package com.example.spring.token.controller;

import com.example.spring.token.config.jwt.JwtProperties;
import com.example.spring.token.config.security.CustomUserDetails;
import com.example.spring.token.domain.entity.User;
import com.example.spring.token.dto.*;
import com.example.spring.token.service.UserService;
import com.example.spring.token.util.CookieUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserApiController {

    private final UserService userService;
    private final JwtProperties jwtProperties;

    @PostMapping("/join")
    public SignUpResponseDto signUp(@RequestBody SignUpRequestDto requestDto) {
        userService.signUp(requestDto);
        return new SignUpResponseDto("/users/login");
    }

    @PostMapping("/login")
    public SignInResponseDto signIn(
            @RequestBody
            SignInRequestDto requestDto,
            HttpServletResponse response
    ) {
        SignInResponseDto signInResponseDto = userService.signIn(requestDto.getUserId(), requestDto.getPassword());

        CookieUtil.addCookie(
                response,
                CookieUtil.REFRESH_TOKEN_COOKIE,
                signInResponseDto.getRefreshToken(),
                (int) jwtProperties.getRefreshTokenValidity().toSeconds()
        );

        return signInResponseDto;
    }

    @PostMapping("/logout")
    public LogoutResponseDto logout(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.deleteCookie(request, response, CookieUtil.REFRESH_TOKEN_COOKIE);
        return LogoutResponseDto.builder().message("로그아웃 되었습니다.").url("/users/login").build();
    }

    @GetMapping("/info")
    public UserInfoResponseDto getUserInfo(
            @AuthenticationPrincipal
            CustomUserDetails userDetails
    ) {
        User user = userDetails.getUser();
        return UserInfoResponseDto.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .userName(user.getName())
                .role(user.getRole())
                .build();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public AuthorityResponseDto authority() {
        return AuthorityResponseDto.builder().message("일반 사용자만 볼 수 있는 권한입니다.").build();
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public AuthorityResponseDto authorityAdmin() {
        return AuthorityResponseDto.builder().message("관리자만 볼 수 있는 권한입니다.").build();
    }
}
