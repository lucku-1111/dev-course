package com.example.spring.webservice.util;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

import java.util.List;

// 이제 쿠키를 만드는 주체는 auth-service이다.
// auth-service가 심은 Set-Cookie는 Feign 호출의 "응답 헤더"에 들어있을 뿐,
// web-service가 브라우저에 보내는 응답과는 별개의 HTTP 응답이라서
// 이렇게 명시적으로 옮겨 실어야 쿠키가 브라우저까지 도달한다.
public class HeaderRelayUtil {

    public static <T> T relaySetCookie(ResponseEntity<T> upstream, HttpServletResponse response) {

        List<String> cookies = upstream.getHeaders().get(HttpHeaders.SET_COOKIE);
        if (cookies != null) {
            cookies.forEach(cookie -> response.setHeader(HttpHeaders.SET_COOKIE, cookie));
        }

        return upstream.getBody();
    }

}
