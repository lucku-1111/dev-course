package com.example.spring.basicboard.mapper;

import com.example.spring.basicboard.domain.entity.Member;
import com.example.spring.basicboard.dto.MemberJoinRequestDto;
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
