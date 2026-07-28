package com.example.spring.formlogin.config.security;

import com.example.spring.formlogin.domain.entity.User;
import lombok.Builder;
import lombok.Getter;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

// * CustomUserDetails를 만드는 이유
// 시큐리티의 인증 검증(DaoAuthenticationProvider)은 우리의 User엔티티를 모른다.
// 대신 UserDetails라는 "표준 인터페이스"로만 사용자 정보를 주고받는다.
// - getUsername()/getPassword() -> 사용자ID/비밀번호 대조에 사용
// - getAuthorities() -> 인가(권한 판단)에 사용
// 즉 이클래스는 우리 도메인(User 엔티티)과 시큐리티 사이의 "어댑터"다
// UserDetailService.loadUserByUsername()이 DB에서 조회한 User를 이걸로 감싸서 반환하면,
// 이후의 비밀번호 대조/권한 판단은 전부 이 객체를 통해 이루어진다.

// 엔티티가 직접 UserDetails를 구현하게 할 수도 있지만(User implements UserDetails),
// 그러면 도메인 엔티티가 시큐리티에 종속된다. 지금처럼 엔티티를 "감싸는" 방식이 도메인과 보안 관심사를 분리할 수 있어 더 낫다.

@Getter
@Builder
public class CustomUserDetails implements UserDetails {

    // 감싸고 있는 원본 엔티티. 아래 메서드들은 전부 이 엔티티의 값을 시큐리티 표준 형태로 변환해 전달한다.
    private User user;

    // 이 사용자가 가진 권한 목록. AuthorizationFilter가 인가 판단할 때 사용한다.
    // "ROLE_" : hasRole("ADMIN") 검사는 내부적으로 "ROLE_ADMIN" 권한을 찾는다.
    // 빈 리스트를 반환하면 로그인은 되지만 권한이 없는 사용자가 되어버린다.
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority( user.getRole().name() )
        );
    }

    // DaoAuthenticationProvider가 passwordEncoder.matches(사용자입력, 이 값)으로 대조한다.
    @Override
    public @Nullable String getPassword() {
        return user.getPassword();
    }

    // 시큐리티가 말하는 username = "사용자를 식별하는 문자열"
    // loadUserByUsername의 username으로 이 값이 들어온다.
    @Override
    public String getUsername() {
        return user.getUserId();
    }

    // ---- 이하 4개는 "계정 상태" 검사용 ----
    // 인증 과정에서 비밀번호 대조 전후에 호출되며, 하나라도 false면 로그인이 거분된다.
    // 우리 서비스는 계정 만료/잠금/비밀번호 만료/비활성화 기능이 없으므로 전부 true로 고정
    // 나중에 "로그인 5회 실패 시 잠금"같은 정책을 넣는다면 User 엔티티에 상태 컬럼을 추가하고
    // 여기서 그 값을 반환하도록 바꾸면 된다.

    // 계정 자체가 만료되지 않았는가 (휴먼 계정 정책 등)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    // 계정이 잠기지 않았는가 (로그인 연속 실패 잠금 등)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    // 비밀번호가 만료되지 않았는가 (주기적 변경 강제 정책 등)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    // 계정이 활성 상태인가 (탈퇴/이메일 미인증 등)
    @Override
    public boolean isEnabled() {
        return true;
    }
}
