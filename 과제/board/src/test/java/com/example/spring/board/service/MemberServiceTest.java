package com.example.spring.board.service;

import com.example.spring.board.domain.entity.Member;
import com.example.spring.board.domain.repository.MemberRepository;
import com.example.spring.board.dto.LoginRequestDto;
import com.example.spring.board.dto.MemberJoinRequestDto;
import com.example.spring.board.exception.DuplicateUserIdException;
import com.example.spring.board.mapper.MemberMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    MemberRepository memberRepository;
    @Mock
    MemberMapper memberMapper;
    @InjectMocks
    MemberService memberService;

    @Test
    @DisplayName("정상 로그인 테스트")
    void login_성공() {
        Member member = Member.builder()
                .userId("hong")
                .password("1234")
                .userName("홍길동")
                .build();
        given(memberRepository.findByUserId("hong")).willReturn(Optional.of(member));

        LoginRequestDto request = new LoginRequestDto();
        request.setUsername("hong");
        request.setPassword("1234");

        Optional<Member> result = memberService.login(request);
        assertThat(result).isPresent();
    }

    @Test
    @DisplayName("비밀번호가 틀리면 빈 옵셔널 반환")
    void login_비밀번호를_틀림() {
        Member member = Member.builder()
                .userId("hong")
                .password("1234")
                .userName("홍길동")
                .build();
        given(memberRepository.findByUserId("hong")).willReturn(Optional.of(member));

        LoginRequestDto request = new LoginRequestDto();
        request.setUsername("hong");
        request.setPassword("1111");

        Optional<Member> result = memberService.login(request);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("없는 아이디면 빈 옵셔널 반환")
    void login_없는_아이디() {
        LoginRequestDto request = new LoginRequestDto();
        request.setUsername("hong");
        request.setPassword("1234");

        Optional<Member> result = memberService.login(request);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("아이디가 중복이 아니면 저장")
    void join_성공() {
        MemberJoinRequestDto request = new MemberJoinRequestDto();
        request.setUserId("hong");
        request.setPassword("1234");
        request.setUserName("홍길동");

        Member member = Member.builder()
                .userId("hong")
                .password("1234")
                .userName("홍길동")
                .build();

        given(memberRepository.existsByUserId("hong")).willReturn(false);
        given(memberMapper.toEntity(request)).willReturn(member);

        memberService.join(request);

        verify(memberRepository).save(member);
    }

    @Test
    @DisplayName("아이디가 중복이면 예외를 던지고 저장하지 않음")
    void join_중복이면_예외() {
        MemberJoinRequestDto request = new MemberJoinRequestDto();
        request.setUserId("hong");
        request.setPassword("1234");
        request.setUserName("홍길동");
        given(memberRepository.existsByUserId("hong")).willReturn(true);

        assertThatThrownBy(() -> memberService.join(request))
                .isInstanceOf(DuplicateUserIdException.class);
        verify(memberRepository, never()).save(any());
    }
}