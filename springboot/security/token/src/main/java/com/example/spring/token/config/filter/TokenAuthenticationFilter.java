package com.example.spring.token.config.filter;


import com.example.spring.token.config.jwt.TokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// * JWT 인증 필터 - 모든 요청마다 한 번 실행
// 이 필터는 "요청에 실려온 토큰"에서 인증 상태를 복원한다.
// 1) Authorization: Bearer <token> 헤더에서 토큰 추출
// 2) 서명/만료 검증(TokenProvider)
// 3) 유효하면 클레임으로 User를 복원해서 SecurityContext에 등록
// -> 이 요청을 처리하는 동안만 "인증된 사용자"가 된다(STATELESS 응답 후 컨텍스트는 버려진다.)
// 4) 토큰이 없거나 무효면 인증없이 통과 -> 보호된 경로라면 체인 끝의 AuthorizationFilter가 거부(401)

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    }
}
