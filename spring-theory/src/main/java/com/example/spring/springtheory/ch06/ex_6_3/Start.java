package com.example.spring.springtheory.ch06.ex_6_3;

// * 스프링 AOP
// * 문제점
// ex_6_2는 부가기능을 적용할 빈마다 ProxyFactoryBean을 '하나씩' 설정해야 한다.
// (target 지정 + advisor 등록을 빈 개수만큼 반복)

// '자동 프록시 생성기'가 프록시 적용을 자동화 한다.

//   - DefaultAdvisorAutoProxyCreator (빈 후처리기):
//       컨테이너의 모든 Advisor를 모아두고, 빈이 만들어질 때마다 그 빈이 advisor의
//       Pointcut에 걸리는지 검사한다. 걸리면 자동으로 프록시로 바꿔 등록한다.
//   - Advisor(Pointcut + Advice)는 빈으로 등록만 해두면 자동으로 수집된다.
//   - target 빈(userService=UserServiceImpl)은 ProxyFactoryBean 없이 '평범하게' 등록.

//   => "어떤 빈을 프록시로 만들지"를 우리가 빈마다 지정하지 않는다.
//      Pointcut 조건에 맞는 빈은 '전부' 자동으로 프록시가 적용된다.
//      서비스가 10개든 100개든 advisor 하나면 끝(부가기능 적용의 완전 자동화).

//   호출 흐름(겉보기는 ex_6_2와 같지만, 프록시를 '자동으로' 만들었다는 점이 다르다):
//     클라이언트 -> (자동 생성된 프록시) -> Advisor의 Pointcut 매칭
//                    -> 매칭되면 TransactionAdvice 적용 -> proceed()로 target 실행
//                 target = UserServiceImpl -> UserDAO

// * 용어 정리
// 이렇게 부가기능(Advice) + 적용대상(Pointcut)을 하나로 묶어
// 여러 객체에 가로질러 적용하는 모듈을 'Aspect'라고 부른다.
// 그래서 AOP(관점 지향 프로그래밍)이다.

//   [왜 '그래서' AOP인가] 패러다임 이름은 '무엇을 모듈 단위로 삼느냐'에서 나온다.
//     - OOP(Object-Oriented Programming): 모듈 단위가 'Object(객체/클래스)' -> 객체 지향.
//     - AOP(Aspect-Oriented Programming): 모듈 단위가 'Aspect'            -> 관점 지향.
//   트랜잭션·로깅 같은 부가기능은 한 클래스에 안 모이고 여러 클래스에 흩어진다(cross-cutting).
//   객체(클래스)로는 이걸 한곳에 모듈화할 수 없어서, '객체를 가로지르는 부가기능'을 담는
//   새 모듈 단위 = Aspect를 도입했다. 그 Aspect를 중심으로 프로그램을 구성하므로 'Aspect 지향(AOP)'이다.

public class Start {
    static void main() {

    }
}
