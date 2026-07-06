package com.example.spring.basicboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// * RESTful (REST, Representational State Transfer)
// 자원을 URI로 표현하고, HTTP메서드로 그 자원 대한 행위를 표현하는 API설계 원칙이다.
// controller의 매핑을 아래 규칙에 맞춰 설계하면 RESTful하다고 한다.

// * 핵심원칙
// - 자원 중심 URI : URI는 명사(자원)로, 동사는 쓰지 않는다. (ex, /boards (O), /getBoard (X))
// - HTTP 메서드로 행위 구분
// - GET : 조회 (예: GET /boards, GET /boards/1)
// - POST : 생성 (예: POST /boards)
// - PUT : 전체 수정 (예: PUT /boards/1)
// - PATCH : 부분 수정 (예: PATCH /boards/1)
// - DELETE : 삭제 (예: DELETE /boards/1)

// * 영속성(Persistence) - 관계 : ORM(개념) > JPA(표준) > Hibernate(구현체)
// 영속성이란?
// - 데이터를 프로그램이 종료돼도 사라지지 않게 영구히 보존하는 성질을 말한다.
// - 자바 객체는 메모리에만 있어 프로그램이 꺼지면 사라진다.(휘발성)
// - 이를 DB같은 영구 저장소에 저장해 다시 꺼내 쓸 수 있게 만드는 것이 영속성이다.
// - 이 "객체 <-> DB" 변환을 담당하는 계층을 영속성 계층이라고 하며, domain.repository 가 그 역할이다.
// - 관련 용어
// 영속화 : 객체를 DB에 저장하는 행위
// 영속성 컨텍스트 : JPA가 엔티티를 관리하는 1차 캐시공간, 엔티티의 상태 변화를 추적해 자동으로 SQL을 실행해 준다.

// * ORM(Object Relational Mapping, 객체-관계 매핑)
// - 자바의 "객체(Object)"와 관계형 DB의 "테이블(Relation)"을 서로 연결(매핑)해 주는 기술이다.
// - 자바 객체(Entity) <-> DB 테이블/행 을 자동으로 매핑하고, 우리가 짠 코드 대신 SQL을 만들어 실행한다.
// - spring-boot-starter-data-jpa를 넣으면 기본 구현체로 하이버네이트가 딸려온다.

// * HikariCP(히카리 커넥션 풀)
// - DB 커넥션은 만들 때마다 비용이 크므로, 미리 여러 개 만들어 두고 빌려주는 "커넥션 풀"을 쓴다
// - HikariCP 는 스프링 부트의 "기본" 커넥션 풀이며 빠르고 가벼워서 널리 쓰인다
// - 요청이 오면 풀에서 커넥션을 빌려 쓰고, 끝나면 반납한다 (매번 새로 연결하지 않아 성능이 좋다)
// - 별도 설정 없이 자동 적용되며, 필요하면 spring.datasource.hikari 아래에서 풀 크기 등을 조정한다

// 계층 구조와 의존성 규칙
// - 프레젠테이션(controller) -> 애플리케이션(service) -> 도메인(domain) 순으로 의존
// - 도메인은 어떤 계층에도 의존하지 않는 가장 안쪽 핵심이다
// - 기술(JPA 등)이 도메인을 침범하지 않도록, 도메인은 인터페이스에만 의존한다

// 패키지 구조 및 요청 처리 흐름
//
// controller  : 프레젠테이션 계층, HTTP 요청/응답 처리, DTO로 입출력
// service     : 애플리케이션 계층, 유스케이스 조율 및 트랜잭션 관리
// dto         : 계층 간 데이터 전송 객체 (요청/응답)
// domain      : 도메인 계층 (핵심 비즈니스 규칙)
//   - entity      : 엔티티 / 애그리거트 (Board, Comment 등)
//   - repository  : 리포지토리 인터페이스 (JpaRepository 상속, 도메인이 규약 정의)
// mapper      : Entity <-> DTO 변환
// exception   : 도메인/공통 예외 처리
// aop         : 공통 관심사(로깅 등)를 @Aspect 로 분리, 학습용이라 System.out 으로만 출력

// 요청 흐름
// 1. controller 가 HTTP 요청을 받아 DTO 로 변환
// 2. service 가 유스케이스를 실행하며 트랜잭션을 관리
// 3. service 가 domain.repository 인터페이스로 엔티티를 조회/저장
// 4. Spring Data JPA 가 인터페이스 구현체를 런타임에 자동 생성해 DB 접근
// 5. entity 를 mapper 로 DTO 변환해 controller 를 통해 응답
//
// aop 는 위 흐름을 가로질러 controller/service 메서드 호출 전후를 가로채 로그를 남긴다
//
// 의존성 방향: controller -> service -> domain.repository(interface) <- JPA 구현
// 도메인이 기술(JPA)에 의존하지 않도록 인터페이스에만 의존한다

@SpringBootApplication
public class BasicBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(BasicBoardApplication.class, args);
    }

}
