package com.example.spring.basicboard.controller;

import com.example.spring.basicboard.constant.SessionConst;
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
                    session.setAttribute(SessionConst.USER_ID, member.getUserId());
                    session.setAttribute(SessionConst.USER_NAME, member.getUserName());
                    return LoginResponseDto.success();
                })
                // 로그인 실패(아이디 없음/비밀번호 불일치) - 이동하지 않고 안내 메시지만 띄운다
                // ---------------------------------------------------------------------
                // [강의] LoginResponseDto::fail 의 '::' 문법 = 메서드 참조(Method Reference)
                // ---------------------------------------------------------------------
                // # 한마디로: "이미 있는 메서드를, 지금 실행하지 말고 '이 메서드를 쓰라'고 이름만 넘기는 것"
                //
                // # orElseGet 은 "값이 없을 때 실행할 함수"를 받는다. 그 함수 자리에 fail 메서드를 넘긴 것이다
                //     .orElseGet(LoginResponseDto::fail)
                //   여기서 fail() 을 "지금 호출"하는 게 아니라(괄호 없음에 주목!),
                //   "나중에 값이 없으면 그때 fail() 을 호출해줘" 라고 메서드 자체를 넘겨준다
                //
                // # 람다로 쓰면 아래와 완전히 똑같은 뜻이다 (셋 다 동일하게 동작):
                //     .orElseGet(() -> LoginResponseDto.fail())   // 람다: "인자 없이 fail() 을 호출하는 함수"
                //     .orElseGet(LoginResponseDto::fail)          // 메서드 참조: 위 람다를 더 짧게 줄인 것
                //   -> 람다 몸통이 "그냥 어떤 메서드 하나를 호출"하는 게 전부라면, 메서드 참조로 압축할 수 있다
                //
                // # 헷갈리기 쉬운 포인트: fail 뒤에 괄호 () 가 없다
                //     LoginResponseDto.fail()  : 지금 즉시 실행해서 "결과값(DTO)"을 넘김
                //     LoginResponseDto::fail   : 실행하지 않고 "메서드 자체(나중에 실행할 것)"를 넘김
                //   orElseGet 은 "필요할 때 실행할 함수"가 필요하므로 괄호 없는 :: 형태를 쓴다
                //
                // # '클래스이름::메서드이름' 형태는 보통 "static 메서드 참조" 다 (fail 이 static 이라 이 형태)
                //   (참고: 인스턴스메서드는 obj::method, 생성자는 클래스이름::new 형태로도 쓸 수 있다)
                .orElseGet(LoginResponseDto::fail);
    }
}
