package com.example.spring.basicboard.service;

import com.example.spring.basicboard.domain.repository.MemberRepository;
import com.example.spring.basicboard.dto.MemberJoinRequestDto;
import com.example.spring.basicboard.exception.DuplicateUserIdException;
import com.example.spring.basicboard.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
// 이 클래스의 "모든 메서드"에 기본 적용된다.
// - readOnly = true의 효과
// "이 트랜잭션은 데이터를 안 바꾼다."라고 JPA한테 알려준다. 이 트랜잭션에서 조회만 하겠다는 힌트 -> 최적화
// 하이버네이트가 변경감지를 위한 스냅샷을 안 만들어 메모리/성능에 유리
// Insert/Update/Delete가 필요한 메서드는 @Transactional을 다시 붙인다.
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberMapper memberMapper;

    @Transactional
    public void join(MemberJoinRequestDto dto) {
        // 아이디 중복체크
        if ( memberRepository.existsByUserId(dto.getUserId()) ) {
            // 예외 공통화
            throw new DuplicateUserIdException("[회원가입] 이미 존재하는 아이디입니다.");
        }

        memberRepository.save( memberMapper.toEntity(dto) );
    }

}
