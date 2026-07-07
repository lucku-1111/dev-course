package com.example.spring.board.service;

import com.example.spring.board.domain.entity.Member;
import com.example.spring.board.domain.repository.MemberRepository;
import com.example.spring.board.dto.LoginRequestDto;
import com.example.spring.board.dto.MemberJoinRequestDto;
import com.example.spring.board.exception.DuplicateUserIdException;
import com.example.spring.board.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Transactional
    public void join(MemberJoinRequestDto request) {
        if (memberRepository.existsByUserId(request.getUserId())) {
            throw new DuplicateUserIdException("이미 존재하는 아이디입니다.");
        }
        memberRepository.save(memberMapper.toEntity(request));
    }

    @Transactional(readOnly = true)
    public Optional<Member> login(LoginRequestDto request) {
        return memberRepository.findByUserId(request.getUsername())
                .filter(member -> member.getPassword().equals(request.getPassword()));
    }
}
