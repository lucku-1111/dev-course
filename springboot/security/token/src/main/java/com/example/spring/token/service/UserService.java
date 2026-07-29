package com.example.spring.token.service;

import com.example.spring.token.domain.repository.UserRepository;
import com.example.spring.token.dto.SignUpRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public void signUp(SignUpRequestDto request) {

    }
}
