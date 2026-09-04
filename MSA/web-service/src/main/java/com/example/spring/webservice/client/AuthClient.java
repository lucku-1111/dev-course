package com.example.spring.webservice.client;

import com.example.spring.webservice.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

// ResponseEntity로 받는 메서드들
// auth-service가 refresh token을 Set-Cookie 헤더로 내려주는 응답과
// 쿠키를 지우는 응답은 본문만 받으면 헤더가 유실된다.
// ResponseEntity로 받아 컨트롤러가 Set-Cookie를 브라우저 응답에 옮겨 실어야한다.
@FeignClient(value = "auth-service", url = "${edge-service.url:http://localhost:8000}")
public interface AuthClient {

    @PostMapping("/api/users/join")
    SignUpResponseDto join(@RequestBody SignUpRequestDto signUpRequestDto);

    @PostMapping("/api/users/login")
    ResponseEntity<SignInResponseDto> login(@RequestBody SignInRequestDto signInRequestDto);

    @GetMapping("/api/users/info")
    UserInfoResponseDto getUserInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization);

    @PostMapping("/api/users/logout")
    ResponseEntity<LogoutResponseDto> logout(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @RequestHeader(HttpHeaders.COOKIE) String cookie
    );

    @PostMapping("/api/tokens/refresh")
    ResponseEntity<RefreshTokenResponseDto> refreshToken(
            @RequestHeader(HttpHeaders.COOKIE) String cookie
    );
}
