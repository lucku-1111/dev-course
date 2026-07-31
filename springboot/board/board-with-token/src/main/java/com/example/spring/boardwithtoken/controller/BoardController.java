package com.example.spring.boardwithtoken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

// 로그인 사용자 정보(userId/userName)는 더 이상 서버가 뷰에 주입하지 않는다.
// - JWT 인증으로 바뀌면서 서버는 세션을 쓰지 않고, 사용자 정보는 각 페이지의 JS 가
//   /api/members/info 를 호출해 직접 채운다. (common.js 의 loadCurrentUser 참고)
@Controller
public class BoardController {

    @GetMapping("/")
    public String boardList() {
        return "board-list";
    }

    @GetMapping("/write")
    public String write() {
        return "board-write";
    }

    @GetMapping("/detail")
    public String detail(
            @RequestParam Long id,
            Model model) {
        model.addAttribute("id", id);
        return "board-detail";
    }

    @GetMapping("/update/{id}")
    public String update(
            @PathVariable Long id,
            Model model
    ) {
        model.addAttribute("id", id);
        return "board-update";
    }
}
