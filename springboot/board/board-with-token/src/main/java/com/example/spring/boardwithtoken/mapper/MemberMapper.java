package com.example.spring.boardwithtoken.mapper;

import com.example.spring.boardwithtoken.domain.entity.Member;
import com.example.spring.boardwithtoken.dto.MemberJoinRequestDto;
import com.example.spring.boardwithtoken.dto.MemberResponseDto;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    public Member toEntity(MemberJoinRequestDto dto, String encodedPassword) {
        return Member.builder()
                .userId(dto.getUserId())
                .password(encodedPassword)
                .userName(dto.getUserName())
                .build();
    }

    public MemberResponseDto toResponseDto(Member member) {
        return MemberResponseDto.builder()
                .id(member.getId())
                .userId(member.getUserId())
                .userName(member.getUserName())
                .role(member.getRole())
                .build();
    }

}
