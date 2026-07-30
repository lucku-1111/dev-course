package com.example.spring.token.service;

import com.example.spring.token.domain.entity.User;
import com.example.spring.token.domain.repository.UserRepository;
import com.example.spring.token.dto.SignUpRequestDto;
import com.example.spring.token.exception.DuplicateUserIdException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(SignUpRequestDto request) {
        if (userRepository.existsByUserId(request.getUserId())) {
            throw new DuplicateUserIdException("[회원가입] 이미 사용 중인 아이디입니다.");
        }

        User user = request.toUser(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
    }
}
