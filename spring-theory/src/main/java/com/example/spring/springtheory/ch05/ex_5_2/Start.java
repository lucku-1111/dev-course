package com.example.spring.springtheory.ch05.ex_5_2;

// * 트렌젝션 서비스 추상화
// * 문제점
// upgradeLevels()가 사용자들을 하나씩 upgrade하다 중간에 실패하면,
// 일부만 반영되는 '부분 실패'가 생긴다.(원자성 미보장)

// '트랜젝션'
// 여러 update를 '하나의 트랜잭션'으로 묶고, 실패 시 전부 롤백한다.

public class Start {
    static void main() {

    }
}
