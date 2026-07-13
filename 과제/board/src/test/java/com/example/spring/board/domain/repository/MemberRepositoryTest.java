package com.example.spring.board.domain.repository;

import com.example.spring.board.domain.entity.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.save(Member.builder()
                .userId("hong")
                .password("1234")
                .userName("홍길동")
                .build());
    }

    @Test
    void existsByUserId() {
        assertTrue(memberRepository.existsByUserId("hong"));
        assertFalse(memberRepository.existsByUserId("kim"));
    }

    @Test
    void findByUserId() {
        assertThat(memberRepository.findByUserId("hong")).isPresent();
        assertThat(memberRepository.findByUserId("kim")).isEmpty();
    }
}