package com.example.spring.boardwithtoken.service;

import com.example.spring.boardwithtoken.config.security.CustomUserDetails;
import com.example.spring.boardwithtoken.domain.entity.Member;
import com.example.spring.boardwithtoken.domain.repository.MemberRepository;
import com.example.spring.boardwithtoken.dto.LoginRequestDto;
import com.example.spring.boardwithtoken.dto.LoginResponseDto;
import com.example.spring.boardwithtoken.dto.MemberJoinRequestDto;
import com.example.spring.boardwithtoken.dto.MemberResponseDto;
import com.example.spring.boardwithtoken.exception.DuplicateUserIdException;
import com.example.spring.boardwithtoken.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    @Transactional
    public void join(MemberJoinRequestDto dto) {
        // 아이디 중복체크
        if ( memberRepository.existsByUserId(dto.getUserId()) ) {
            // 예외 공통화
            throw new DuplicateUserIdException("[회원가입] 이미 존재하는 아이디입니다.");
        }
        memberRepository.save(memberMapper.toEntity(dto, passwordEncoder.encode(dto.getPassword())));
    }

    public LoginResponseDto login(LoginRequestDto dto) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword())
        );

        Member member = ((CustomUserDetails) authenticate.getPrincipal()).getMember();

        TokenService.TokenPair tokenPair = tokenService.issueToken(member);

        return LoginResponseDto.builder()
                .isLoggedIn(true)
                .message("로그인 성공")
                .url("/")
                .accessToken(tokenPair.accessToken())
                .refreshToken(tokenPair.refreshToken())
                .userName(member.getUserName())
                .userId(member.getUserId())
                .build();
    }

    public List<MemberResponseDto> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(memberMapper::toResponseDto)
                .toList();
    }
}
