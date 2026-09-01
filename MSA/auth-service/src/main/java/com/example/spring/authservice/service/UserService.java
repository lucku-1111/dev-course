package com.example.spring.authservice.service;

import com.example.spring.authservice.config.security.CustomUserDetails;
import com.example.spring.authservice.domain.entity.User;
import com.example.spring.authservice.domain.repository.UserRepository;
import com.example.spring.authservice.dto.SignInRequestDto;
import com.example.spring.authservice.dto.SignInResponseDto;
import com.example.spring.authservice.dto.SignUpRequestDto;
import com.example.spring.authservice.dto.UserNameResponseDto;
import com.example.spring.authservice.exception.DuplicateUserIdException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Transactional
    public void signUp(SignUpRequestDto signUpRequestDto) {

        if ( userRepository.existsByUserId( signUpRequestDto.getUserId() ) ) {
            throw new DuplicateUserIdException("[회원가입] 이미 사용중인 아이디입니다.");
        }

        User user = signUpRequestDto.toUser(passwordEncoder.encode(signUpRequestDto.getPassword()));

        userRepository.save(user);
    }

    @Transactional
    public SignInResponseDto login(SignInRequestDto signInRequestDto) {

        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInRequestDto.getUserId(), signInRequestDto.getPassword())
        );

        User user = ((CustomUserDetails) authenticate.getPrincipal()).getUser();

        TokenService.TokenPair tokenPair = tokenService.issueToken(user);

        return SignInResponseDto.builder()
                .loggedIn(true)
                .message("로그인 성공")
                .url("/")
                .accessToken(tokenPair.accessToken())
                .refreshToken(tokenPair.refreshToken())
                .userId(user.getUserId())
                .userName(user.getName())
                .build();
    }

    public List<UserNameResponseDto> getUserNames(List<String> userIds) {
        return userRepository.findByUserIdIn(userIds).stream()
                .map( user -> UserNameResponseDto.builder()
                        .userId(user.getUserId())
                        .userName(user.getName())
                        .build())
                .toList();
    }

}
