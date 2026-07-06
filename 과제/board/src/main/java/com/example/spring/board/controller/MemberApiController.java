package com.example.spring.board.controller;

import com.example.spring.board.dto.MemberJoinRequestDto;
import com.example.spring.board.dto.MemberJoinResponseDto;
import com.example.spring.board.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
