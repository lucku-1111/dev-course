package com.example.spring.edgeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// * MSA(Microservice Architecture)란?
// 하나의 애플리케이션을, 각자 독립적으로 배포/확장/실패 할 수 있는
// 작은 서비스들의 협력으로 바꾸는 아키텍처 스타일이다.

// 모놀리스 : 전 기능이 한 프로세스/한 배포 단위/한 DB. 메서드 호출로 협력한다.
// -> 단순하고 빠르다.(트랜잭션 하나로 정합성 해결). 문제는 "전부가 한 몸"이라는 것
// -> 한 기능의 배포가 전체 재배포이고, 한 기능의 부하가 전체를 느리게 하고,
// -> 한 기능의 장애가 프로세스 전체를 죽인다.
// MSA : 기능을 프로세스 경계로 가른다. 협력은 메서드 호출이 아니라 네트워크(HTTP/이벤트)로.

// MSA가 왜 필요한가?
// 1. 독립 배포 : 게시판 검색 로직을 고치는 데 인증 서버를 재배포할 이유가 없다.
// 배포 단위가 작아지면 배포 빈도는 오르고 실패 반경은 준다.
// 2. 독립 확장 : 조회 폭주는 board만 스케일 아웃하면 된다. 모놀리스는 전체를 복제해야 한다.
// 3. 장애 격리(전파 방지) : 한 서비스의 죽음이 "전체의 죽음"이 아니라 "부분 기능 저하"가 되게 한다.
// 단, 공짜가 아니다. - 프로세스를 갈랐다고 저절로 격리되지 않는다.
// 동기 호출은 장애를 그대로 전파하므로, 격리는 설계해야 한다..
// - 폴백 : auth가 죽어도 board 목록은 뜬다 - 작성자 이름만 비운 상태로
// - 보상 : board가 죽어도 탈퇴는 안전하게 실패한다. - 계정 상태 원복 후 503 (탈퇴 Saga)

// Database per Service - 데이터도 서비스 경계를 따른다.
// 원칙 : 각 서비스는 자기 데이터베이스를 소유하고, 다른 서비스의 DB에는 직접 접근하지 않는다.

@SpringBootApplication
public class EdgeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(EdgeServiceApplication.class, args);
    }

}
