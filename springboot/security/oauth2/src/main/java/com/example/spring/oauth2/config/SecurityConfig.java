package com.example.spring.oauth2.config;

// * OAuth2 (Open Authorization 2.0)
// OAuth2는 "비밀번호를 넘겨주지 안고 권한을 위임"하기 위한 표준 프레임워크
// OAuth2 사용 방법:
// 사용자가 "카카오에게 직접" 허락받고
// 우리 서비스는 그 허락의 증표(access token)만 받는다.
// -> 비밀번호는 원래 주인(카카오)만 알고, 위임 범위와 회수가 가능해진다.

// 역할(role)
// 1) Resource Owner : 자원의 주인 = 사용자 (카카오 계정의 주인)
// 2) Client : 자원을 쓰고 싶은 제3자 앱 = 우리 서비스
// 3) Authorization Server : 허락(인가)을 발급하는 서버 = kauth.kakao.com
// 4) Resource Server : 실제 자원(프로필 등)을 보관한 서버 = kapi.kakao.com

// 인가 코드 방식 - 표준 흐름
// 1) Client가 사용자를 Authorization Server의 인가 페이지로 리다이렉트
// (client_id, redirect_uri, scope, state를 쿼리로 실어 보낸다.)
// 2) 사용자가 "카카오 화면에서" 로그인하고 권한 제공에 동의
// 3) Authorization Server가 redirect_uri로 "인가 코드"를 돌려준다 (브라우저 공유)
// 4) Client 서버가 code + client_secret으로 토큰 엔드포인트에 직접 요청(브라우저 안 거침)
// 5) Authorization Server가 access token 발급
// 6) Client가 그 토큰으로 Resource Server에서 사용자 정보 조회

// Spring Security에서의 동작 흐름 - oauth2Login()
// 위 표준 흐름을 필터 두 개가 나눠서 대신 처리한다.
// 1) OAuth2AuthorizationRequestRedirectFilter
// 2) OAuth2LoginAuthenticationFilter
// 3) 조회된 사용자 정보를 OAuth2UserService.loadUser()에 넘긴다.
// 4) 반환된 OAuth2User로 Athentication을 만들어 SecurityContext에 저장 -> 로그인완료
// 5) 마지막으로 SuccessHandler 호출 -> 로그인 후처리(JWT 발급)도 개발자의 몫

public class SecurityConfig {
}
