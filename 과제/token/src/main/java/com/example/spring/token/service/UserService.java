package com.example.spring.token.service;

import com.example.spring.token.config.security.CustomUserDetails;
import com.example.spring.token.domain.entity.User;
import com.example.spring.token.domain.repository.UserRepository;
import com.example.spring.token.dto.SignInResponseDto;
import com.example.spring.token.dto.SignUpRequestDto;
import com.example.spring.token.exception.DuplicateUserIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Transactional
    public void signUp(SignUpRequestDto request) {
        if (userRepository.existsByUserId(request.getUserId())) {
            throw new DuplicateUserIdException("[회원가입] 이미 사용 중인 아이디입니다.");
        }

        User user = request.toUser(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }
    
    public SignInResponseDto signIn(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        User user = ((CustomUserDetails) authentication.getPrincipal()).getUser();

        TokenService.TokenPair tokens = tokenService.issueToken(user);

        return SignInResponseDto.builder()
                .isLoggedIn(true).message("로그인 성공").url("/")
                .accessToken(tokens.accessToken())
                .refreshToken(tokens.refreshToken())
                .userId(user.getUserId()).userName(user.getName())
                .build();
    }
}
