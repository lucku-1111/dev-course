package com.example.spring.basicboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberJoinRequestDto {
    private String userId;
    private String password;
    private String userName;
}
