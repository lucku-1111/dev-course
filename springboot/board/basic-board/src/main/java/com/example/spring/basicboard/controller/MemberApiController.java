package com.example.spring.basicboard.controller;

import com.example.spring.basicboard.dto.LoginRequestDto;
import com.example.spring.basicboard.dto.LoginResponseDto;
import com.example.spring.basicboard.dto.MemberJoinReponseDto;
import com.example.spring.basicboard.dto.MemberJoinRequestDto;
import com.example.spring.basicboard.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;

    @PostMapping("/join")
    public MemberJoinReponseDto join(@RequestBody MemberJoinRequestDto dto) {
        memberService.join(dto);
        return new MemberJoinReponseDto("/members/login");
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto dto, HttpSession session) {
        return memberService.login(dto)
                .map(member -> {
                    session.setAttribute("userId", member.getUserId());
                    session.setAttribute("userName", member.getUserName());
                    return LoginResponseDto.success();
                })
                .orElseGet(LoginResponseDto::fail);
    }

}
