package com.example.spring.basicboard.controller;

import com.example.spring.basicboard.dto.MemberJoinReponseDto;
import com.example.spring.basicboard.dto.MemberJoinRequestDto;
import com.example.spring.basicboard.service.MemberService;
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
        memberService.join( dto );
        return new MemberJoinReponseDto("/members/login");
    }

}
