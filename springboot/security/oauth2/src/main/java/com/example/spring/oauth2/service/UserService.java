package com.example.spring.oauth2.service;

import com.example.spring.oauth2.config.security.CustomUserDetails;
import com.example.spring.oauth2.domain.entity.User;
import com.example.spring.oauth2.domain.repository.UserRepository;
import com.example.spring.oauth2.dto.SignInRequestDto;
import com.example.spring.oauth2.dto.SignInResponseDto;
import com.example.spring.oauth2.dto.SignUpRequestDto;
import com.example.spring.oauth2.exception.DuplicateUserIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @Transactional
    public void signUp(SignUpRequestDto request) {
        if (userRepository.existsByUserId(request.getUserId())) {
            throw new DuplicateUserIdException("[회원가입] 이미 사용중인 아이디입니다.");
        }

        User user = request.toUser(passwordEncoder.encode(request.getPassword()));

        userRepository.save(user);
    }

    public SignInResponseDto login(SignInRequestDto requestDto) {

        // form-login에서는 필터가 하던 아이디/비밀번호 검증을 직접 호출한다.
        // 실패하면 AuthenticationException이 던져진다.
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getUserId(), requestDto.getPassword())
        );

        User user = ((CustomUserDetails) authenticate.getPrincipal()).getUser();

        TokenService.TokenPair tokenPair = tokenService.issueToken(user);

        return SignInResponseDto.builder()
                .isLoggedIn(true)
                .message("로그인 성공")
                .url("/")
                .accessToken(tokenPair.accessToken())
                .refreshToken(tokenPair.refreshToken())
                .userName(user.getName())
                .userId(user.getUserId())
                .build();
    }
}
