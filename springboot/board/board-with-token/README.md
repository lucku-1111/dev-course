# Basic Board — Spring Security & JWT

## 보안 설계 핵심

| 구분 | 적용 방식 |
|---|---|
| 세션 정책 | STATELESS |
| 비밀번호 | BCrypt 단방향 암호화 |
| Access Token | 로그인 응답 JSON으로 전달하고 Local Storage에 저장 |
| Refresh Token | HttpOnly Cookie로 전달 |
| API 인증 | Authorization: Bearer {accessToken} |
| 토큰 재발급 | Refresh Token 검증 후 Access/Refresh Token 동시 재발급 |
| 게시글 수정 인가 | 작성자 본인만 가능 |
| 회원 목록 조회 인가 | ROLE_ADMIN만 가능 |

> 화면에서 버튼을 숨기는 것은 사용자 편의를 위한 처리입니다. 실제 보안은 항상 서버의 인가 검증이 담당합니다.

## 1. 회원가입

~~~mermaid
sequenceDiagram
    participant Browser
    participant MemberApiController
    participant MemberService
    participant PasswordEncoder
    participant MemberRepository

    Browser->>MemberApiController: POST /api/members/join
    MemberApiController->>MemberService: join(dto)
    MemberService->>MemberRepository: existsByUserId(userId)

    alt 중복 아이디
        MemberRepository-->>MemberService: true
        MemberService-->>Browser: 409 Conflict
    else 사용 가능한 아이디
        MemberService->>PasswordEncoder: encode(rawPassword)
        PasswordEncoder-->>MemberService: BCrypt Password
        MemberService->>MemberRepository: save(Member, ROLE_USER)
        MemberService-->>Browser: 200 OK
    end
~~~

## 2. 로그인과 토큰 발급

~~~mermaid
sequenceDiagram
    participant Browser
    participant MemberApiController
    participant MemberService
    participant AuthenticationManager
    participant UserDetailService
    participant TokenService
    participant TokenProvider

    Browser->>MemberApiController: POST /api/members/login
    MemberApiController->>MemberService: login(dto)
    MemberService->>AuthenticationManager: authenticate(username, password)
    AuthenticationManager->>UserDetailService: loadUserByUsername(username)
    UserDetailService-->>AuthenticationManager: CustomUserDetails
    AuthenticationManager-->>MemberService: 인증 성공
    MemberService->>TokenService: issueToken(member)
    TokenService->>TokenProvider: Access Token 생성
    TokenService->>TokenProvider: Refresh Token 생성
    TokenService-->>MemberApiController: TokenPair
    MemberApiController-->>Browser: Access Token JSON + Refresh Token Cookie
~~~

## 3. 보호 API 인증

~~~mermaid
sequenceDiagram
    participant Browser
    participant TokenAuthenticationFilter
    participant TokenProvider
    participant SecurityContext
    participant Controller

    Browser->>TokenAuthenticationFilter: Authorization: Bearer Access Token
    TokenAuthenticationFilter->>TokenProvider: validateToken(token)

    alt 유효한 토큰
        TokenProvider-->>TokenAuthenticationFilter: VALID
        TokenAuthenticationFilter->>TokenProvider: getTokenDetails(token)
        TokenProvider-->>TokenAuthenticationFilter: Member
        TokenAuthenticationFilter->>SecurityContext: Authentication 저장
        TokenAuthenticationFilter->>Controller: 요청 계속
        Controller-->>Browser: 200 OK
    else 만료된 토큰
        TokenProvider-->>TokenAuthenticationFilter: EXPIRED
        TokenAuthenticationFilter-->>Browser: 401 Unauthorized
    else 토큰 없음 / 잘못된 토큰
        TokenProvider-->>TokenAuthenticationFilter: INVALID / 토큰 없음
        TokenAuthenticationFilter->>Controller: 인증 정보 없이 요청 계속
        Controller-->>Browser: 401 Unauthorized (인증 필요)
    end
~~~

## 4. Access Token 재발급

common.js는 보호 API가 401을 반환하면 Refresh Token으로 토큰 재발급을 한 번 시도합니다.

~~~mermaid
sequenceDiagram
    participant Browser
    participant CommonJS
    participant ProtectedAPI
    participant TokenAPI
    participant TokenService

    Browser->>CommonJS: 보호 API 호출
    CommonJS->>ProtectedAPI: 만료된 Access Token
    ProtectedAPI-->>CommonJS: 401 Unauthorized
    CommonJS->>TokenAPI: POST /api/tokens/refresh + HttpOnly Cookie
    TokenAPI->>TokenService: refreshToken(cookies)

    alt Refresh Token 유효
        TokenService-->>TokenAPI: 새 Access/Refresh Token
        TokenAPI-->>CommonJS: 새 Access Token + 새 Cookie
        CommonJS->>ProtectedAPI: 원래 요청 한 번 재시도
        ProtectedAPI-->>Browser: 정상 응답
    else Refresh Token 없음·만료·위조
        TokenService-->>TokenAPI: validated=false
        TokenAPI-->>CommonJS: 401 Unauthorized
        CommonJS->>CommonJS: Access Token 삭제
        CommonJS-->>Browser: /members/login 이동
    end
~~~

## 5. 로그아웃

~~~mermaid
sequenceDiagram
    participant Browser
    participant MemberApiController
    participant CookieUtil

    Browser->>MemberApiController: POST /api/members/logout
    MemberApiController->>CookieUtil: Refresh Token Cookie 삭제
    MemberApiController-->>Browser: 200 OK
    Browser->>Browser: Local Storage Access Token 삭제
    Browser->>Browser: /members/login 이동
~~~

## 6. 게시글 수정 인가 (리소스 소유자 기반)

~~~mermaid
flowchart TD
    Request["게시글 수정 요청 PUT /api/boards/{id}"]
    Authenticated{"인증된 사용자?"}
    Owner{"작성자 본인과 일치?"}
    Allow["수정 허용"]
    Unauthorized["401 Unauthorized"]
    Forbidden["403 Forbidden"]

    Request --> Authenticated
    Authenticated -->|아니오| Unauthorized
    Authenticated -->|예| Owner
    Owner -->|예| Allow
    Owner -->|아니오| Forbidden
~~~

작성자 판별은 클라이언트가 보낸 값이 아니라, 토큰에서 꺼낸 `@AuthenticationPrincipal` 값을 기준으로 한다.

## 7. 회원 목록 조회 인가 (역할 기반)

~~~mermaid
flowchart TD
    Request["회원 목록 조회 GET /api/members"]
    Authenticated{"인증된 사용자?"}
    Admin{"ROLE_ADMIN?"}
    Allow["회원 목록 응답"]
    Unauthorized["401 Unauthorized"]
    Forbidden["403 Forbidden"]

    Request --> Authenticated
    Authenticated -->|아니오| Unauthorized
    Authenticated -->|예| Admin
    Admin -->|예| Allow
    Admin -->|아니오| Forbidden
~~~

`@PreAuthorize("hasRole('ADMIN')")`로 선언적으로 적용했다.

## 8. 공개 경로와 보호 경로

### 인증 없이 접근 가능

| Method | 경로 | 설명 |
|---|---|---|
| GET | /, /write, /detail, /update/**, /admin/members | 화면 페이지(내부 데이터 API는 인증 필요) |
| GET | /members/login | 로그인 화면 |
| GET | /members/join | 회원가입 화면 |
| GET | /members/logout | 로그아웃(레거시 세션 무효화 라우트) |
| GET | /access-denied | 인증/인가 실패 시 이동하는 안내 페이지 |
| GET | /css/** | CSS |
| GET | /js/** | JavaScript |
| POST | /api/members/join | 회원가입 |
| POST | /api/members/login | 로그인 |
| POST | /api/tokens/refresh | 토큰 재발급 (Refresh Token 쿠키로 검증) |

SecurityConfig의 로그인 화면 공개 경로는 `/members/login`으로 고정한다.

### 인증 필요

| Method | 경로 | 설명 |
|---|---|---|
| GET | /api/members/info | 로그인 사용자 정보 |
| GET | /api/members | 회원 목록 조회 (ROLE_ADMIN) |
| POST | /api/members/logout | 로그아웃 |
| GET | /api/boards/** | 게시글 조회·검색·파일 다운로드 |
| POST | /api/boards | 게시글 작성 |
| PUT | /api/boards/{id} | 게시글 수정 (작성자 본인) |
| DELETE | /api/boards/{id} | 게시글 삭제 |
| POST | /api/boards/{boardId}/comments | 댓글 작성 |
