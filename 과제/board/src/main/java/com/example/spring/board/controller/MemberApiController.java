package com.example.spring.board.controller;

import com.example.spring.board.constant.SessionConst;
import com.example.spring.board.dto.LoginRequestDto;
import com.example.spring.board.dto.LoginResponseDto;
import com.example.spring.board.dto.MemberJoinRequestDto;
import com.example.spring.board.dto.MemberJoinResponseDto;
import com.example.spring.board.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/join")
    MemberJoinResponseDto join(@RequestBody MemberJoinRequestDto request) {
        memberService.join(request);
        return new MemberJoinResponseDto("/members/login");
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto request, HttpSession session) {
        return memberService.login(request)
                .map(member -> {
                    session.setAttribute(SessionConst.USER_ID, member.getUserId());
                    session.setAttribute(SessionConst.USER_NAME, member.getUserName());
                    return LoginResponseDto.success();
                })
                .orElseGet(LoginResponseDto::fail);
    }
}
