package com.example.spring.boardwithtoken.controller;

import com.example.spring.boardwithtoken.config.jwt.JwtProperties;
import com.example.spring.boardwithtoken.config.security.CustomUserDetails;
import com.example.spring.boardwithtoken.constant.SessionConst;
import com.example.spring.boardwithtoken.dto.*;
import com.example.spring.boardwithtoken.mapper.MemberMapper;
import com.example.spring.boardwithtoken.service.MemberService;
import com.example.spring.boardwithtoken.util.CookieUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "회원 API", description = "회원가입, 로그인, 로그아웃 (세션 기반, spring security 미사용)")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberApiController {

    private final MemberService memberService;
    private final JwtProperties jwtProperties;
    private final MemberMapper memberMapper;

    @Operation(summary = "회원가입", description = "아이디/비밀번호/이름으로 새 회원을 등록한다. 성공 시 로그인 페이지 경로를 돌려준다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "가입 성공"),
            @ApiResponse(responseCode = "409", description = "이미 존재하는 아이디",
                    content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/join")
    public MemberJoinReponseDto join(@RequestBody MemberJoinRequestDto dto) {
        memberService.join(dto);
        return new MemberJoinReponseDto("/members/login");
    }

    @Operation(summary = "로그인",
            description = "아이디/비밀번호로 로그인한다. 성공 시 세션에 사용자 정보를 저장하고 loggedIn=true 를, 실패 시 loggedIn=false 와 안내 메시지를 돌려준다.")
    @PostMapping("/login")
    public LoginResponseDto login(
            @RequestBody LoginRequestDto dto,
            HttpServletResponse response
    ) {
        LoginResponseDto loginResponseDto = memberService.login(dto);

        CookieUtil.addCookie(
                response,
                CookieUtil.REFRESH_TOKEN_COOKIE,
                loginResponseDto.getRefreshToken(),
                (int) jwtProperties.getRefreshTokenValidity().toSeconds()
        );

        loginResponseDto.setRefreshToken(null);

        return loginResponseDto;
    }

    @PostMapping("/logout")
    public LogoutResponseDto logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        CookieUtil.deleteCookie(request, response, CookieUtil.REFRESH_TOKEN_COOKIE);
        return LogoutResponseDto.builder()
                .message("로그아웃 되었습니다.")
                .url("/members/login")
                .build();
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/info")
    public MemberResponseDto getMyInfo(@AuthenticationPrincipal CustomUserDetails principal) {
        return memberMapper.toResponseDto(principal.getMember());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<MemberResponseDto> getMembers() {
        return memberService.getAllMembers();
    }
}
