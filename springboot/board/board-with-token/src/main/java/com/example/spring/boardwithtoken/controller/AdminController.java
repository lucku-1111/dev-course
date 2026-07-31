package com.example.spring.boardwithtoken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 화면(뷰) 자체는 permitAll 이다. 실제 데이터 접근(GET /api/members)이
// @PreAuthorize("hasRole('ADMIN')") 로 막혀있으므로, 여기 진입해도 관리자가 아니면 목록이 안 뜬다.
// (자세한 이유는 SecurityConfig 의 permitAll 목록 주석 참고 - 토큰은 JS ajax 요청에만 실려간다)
@Controller
public class AdminController {

    @GetMapping("/admin/members")
    public String members() {
        return "admin-members";
    }
}