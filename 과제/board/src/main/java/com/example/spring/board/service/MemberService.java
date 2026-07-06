package com.example.spring.board.service;

import com.example.spring.board.domain.repository.MemberRepository;
import com.example.spring.board.dto.MemberJoinRequestDto;
import com.example.spring.board.exception.DuplicateUserIdException;
import com.example.spring.board.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Transactional
    public void join(MemberJoinRequestDto dto) {
        if (memberRepository.existsByUserId(dto.getUserId())) {
            throw new DuplicateUserIdException("이미 존재하는 아이디입니다.");
        }
        memberRepository.save(memberMapper.toEntity(dto));
    }
}
