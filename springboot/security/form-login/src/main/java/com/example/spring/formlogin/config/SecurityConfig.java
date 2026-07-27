package com.example.spring.formlogin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

// * 폼 로그인이란?
// 개발자가 직접 만든 HTML 로그인 화면(폼)을 통해 아이디/비밀번호를 받아 인증하는 방식이다.
// HTTP Basic이 브라우저 기본 팝업 + 헤더 방식이었다면, 폼 로그인은 우리가 디자인한 페이지 + 세션 기반이라는 점이 가장 큰 차이.
// 사람이 사용하는 일반적인 웹 애플리케이션의 표준 방식이다.

@Configuration
@EnableWebSecurity
public class SecurityConfig {
}
