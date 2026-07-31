package com.example.spring.boardwithtoken.dto;

import com.example.spring.boardwithtoken.domain.entity.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MemberResponseDto {

    @Schema(description = "회원 id", example = "1")
    private Long id;

    @Schema(description = "회원 아이디", example = "user01")
    private String userId;

    @Schema(description = "회원 이름", example = "홍길동")
    private String userName;

    @Schema(description = "권한", example = "ROLE_USER")
    private Role role;
}
