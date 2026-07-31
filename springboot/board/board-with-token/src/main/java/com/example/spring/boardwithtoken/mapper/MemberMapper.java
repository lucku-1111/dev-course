package com.example.spring.boardwithtoken.mapper;

import com.example.spring.boardwithtoken.domain.entity.Member;
import com.example.spring.boardwithtoken.dto.MemberJoinRequestDto;
import org.springframework.stereotype.Component;

@Component
public class MemberMapper {

    // DTO -> Entity
    public Member toEntity(MemberJoinRequestDto dto) {
        return Member.builder()
                .userId(dto.getUserId())
                .password(dto.getPassword())
                .userName(dto.getUserName())
                .build();
    }

}
