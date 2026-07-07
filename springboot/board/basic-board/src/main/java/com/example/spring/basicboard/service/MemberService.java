package com.example.spring.basicboard.service;

import com.example.spring.basicboard.domain.entity.Member;
import com.example.spring.basicboard.domain.repository.MemberRepository;
import com.example.spring.basicboard.dto.LoginRequestDto;
import com.example.spring.basicboard.dto.MemberJoinRequestDto;
import com.example.spring.basicboard.exception.DuplicateUserIdException;
import com.example.spring.basicboard.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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

    // * Optional<Member>: NPE(NullPointException 예방
    // - 예전에는 "값이 없음"을 null로 표현했는데, null을 깜빡하고 그냥 쓰면 실행 중에 NPE가 터진다.
    // 예) Member m = findByUserId("test"); m.getUserName(); // m이 null이면 터진다.
    // - 게다가 반환 타입만 봐서는 "null이 올 수 있는지" 알 수가 없어서 실수하기가 쉬웠다.

    // Optional - "값이 없을 수도 있다"를 타입으로 알려주는 상자(Wrapper)
    // - 반환 타입이 Optional이면 "값이 없을 수 있으니 처리해라"라고 컴파일 단계에서 강제된다.
    // - 즉 '없을 수 있음'을 문서가 아니라 "타입"으로 표현해 실수를 막는 장치이다.

    // 상자(Wrapper)를 여는(값을 거내는) 주요 메서드
    // - isPresent()/isEmpty(): 값이 있는지/없는지 boolean으로 확인
    // - get(): 값을 꺼냄(비어있으면 예외! 되도록 쓰지 않는다.)
    // - orElse(기본값): 있으면 그 값, 없으면 기본값
    // - orElseGet(함수): 있으면 그 값, 없으면 함수를 실행(없을 때만 계산)
    // - map(함수): 값이 있으면 다른 값으로 변환, 없으면 그대로 empty
    // - filter(조건): 값이 있고 조건을 만족하면 유지, 아니면 empty

    // 세 가지 상태
    // - Optional.of(값): 값이 확실히 있을 때(값이 Null이면 즉시 예외)
    // - Optional.empty() : 빈 상자(값이 없음)
    // - Optional.ofNullable(값): 값이 null 일 수도 있을 때(null이면 empty, 아니면 of)

    // 주의 (자주 하는 실수)
    //   - get() 남발 금지: 비어있으면 예외다. orElse* 계열로 안전하게 꺼낸다
    //   - if(opt.isPresent()) opt.get() 패턴은 사실상 null 체크와 다를 게 없다 -> map/orElseGet 으로 흘려보내자
    //   - 보통 "반환 타입"에만 쓴다. 필드나 파라미터 타입으로는 잘 쓰지 않는다 (설계 관례)

    public Optional<Member> login(LoginRequestDto dto) {

        // return 한 줄에 쓰인 람다식과 Optional.filter 이해하기
        // ---------------------------------------------------------------------------------
        // # findByUserId 는 Optional<Member> 를 돌려준다 (회원이 있을 수도, 없을 수도)
        // # .filter( member -> 조건 ) 의 동작:
        //     - Optional 안에 값이 "있고" + 람다가 true  -> 그 값을 그대로 유지
        //     - 값이 "없거나"          + 람다가 false -> 빈 Optional(Optional.empty)로 만든다
        //   => "아이디로 찾은 회원이 있고, 비밀번호까지 일치하면 남기고, 아니면 비운다" = 로그인 성공/실패 판정

        // # member -> member.getPassword().equals(...) 가 바로 람다식(이름 없는 함수)이다
        //     member                         : 입력 파라미터 (Optional 안에 든 Member)
        //     ->                             : "이것을 받아서 ~를 반환한다"
        //     member.getPassword().equals(..): 반환값 (boolean). 비밀번호가 같으면 true

        // # 만약 람다(와 Optional)를 쓰지 않았다면, 아래처럼 풀어 쓴 것과 같다:
        //
        //     // 1) 아이디로 회원을 조회한다 (없으면 null 이 나오도록 orElse(null) 사용)
        //     Member member = memberRepository.findByUserId(request.getUsername()).orElse(null);
        //
        //     // 2) 회원이 존재하고(null 아님) + 비밀번호가 일치하면 -> 로그인 성공
        //     if (member != null && member.getPassword().equals(request.getPassword())) {
        //         return Optional.of(member);   // 성공: 회원을 담아 반환
        //     }
        //
        //     // 3) 아이디가 없거나 비밀번호가 틀리면 -> 로그인 실패
        //     return Optional.empty();          // 실패: 빈 Optional 반환
        //
        //   => 위 if 분기(널 체크 + 비밀번호 비교)를 .filter(람다) 한 줄로 압축한 것이 아래 코드다
        return memberRepository.findByUserId(dto.getUsername())
                .filter(member -> member.getPassword().equals(dto.getPassword()));
    }
}
