package com.example.spring.essentials.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

// * 세션(Session)이란?
// HTTP는 '무상태'라 각 요청이 서로를 기억하지 못한다
// 그래서 "방금 로그인한 사람"을 다음 요청에서도 알아보려면 별도 장치가 필요하다
// 그 장치가 바로 세션이다.
// - 세션은 '서버 쪽 메모리'에 사용자별 정보를 저장해 두는 보관함이다.
// - 사용자가 처음 접속하면 서버는 고유한 세션 ID를 발급하고,
// 그 ID를 브라우저에 쿠키(JSESSIONID)로 심어 준다.
// - 이후 브라우저는 매 요청마다 이 쿠키를 자동으로 함께 보내고,
// 서버는 그 ID로 '이 사람의 보관함'을 찾아 로그인 상태 등을 기억한다.
// 즉 실제 데이터는 서버에 있고, 브라우저는 '보관함 열쇠(ID)'만 들고 다니는 셈이다.

// * 스프링에서 세션 다루기
//   - 컨트롤러 메서드의 매개변수로 HttpSession 을 선언하면 스프링이 자동으로 넣어 준다.
//   - session.setAttribute("키", 값)  : 세션에 값을 저장한다. (로그인 시 사용자 저장 등)
//   - session.getAttribute("키")      : 저장한 값을 꺼낸다. 없으면 null 을 돌려준다.
//   - session.invalidate()            : 세션을 통째로 비운다. (로그아웃 시 사용)
@Controller
public class SessionController {

    @GetMapping("/login")
    public String login(
            HttpSession session,
            Model model
    ) {
        System.out.println("login page : " + session.getAttribute("username"));

        String username = (String) session.getAttribute("username");

        if (username != null) {
            model.addAttribute("username", username);
        }

        return "login";
    }

    @PostMapping("/login")
    public String loginExc(
            @RequestParam String username,
            HttpSession session
    ) {
        System.out.println("user name : " + username);
        session.setAttribute("username", username);

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // 무효화
        session.invalidate();

        return "redirect:/login";
    }

}
