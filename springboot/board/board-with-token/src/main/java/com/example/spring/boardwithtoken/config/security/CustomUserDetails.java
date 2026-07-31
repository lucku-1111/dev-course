package com.example.spring.boardwithtoken.config.security;

import com.example.spring.boardwithtoken.domain.entity.Member;
import lombok.Builder;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Builder
public class CustomUserDetails implements UserDetails {

    private Member member;

    // 이 사용자가 가진 권한 목록. AuthorizationFilter가 인가 판단할 때 사용한다.
    // "ROLE_" : hasRole("ADMIN") 검사는 내부적으로 "ROLE_ADMIN" 권한을 찾는다.
    // 빈 리스트를 반환하면 로그인은 되지만 권한이 없는 사용자가 되어버린다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority(member.getRole().name())
        );
    }

    @Override
    public @Nullable String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
