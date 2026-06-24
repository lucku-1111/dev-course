package com.example.spring.springtheory.ch02;

import com.example.spring.springtheory.ch02.dao.DaoFactory;
import com.example.spring.springtheory.ch02.dao.UserDAO;
import com.example.spring.springtheory.ch02.domain.User;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.sql.SQLException;

// * 문제점
// Start.java 테스트의 문제점
// - 수동 확인 작업의 번거로움
// 콘솔에 나온 값을 보고 등록과 조회가 성공적으로 되고 있는지를 확인하는 건 사람의 책임이다.
// - 실행 작업의 번거로움
// 만약 DAO가 수백 개가 되고 그에 대한 main() 메서드도 그만큼 만들어진다면,
// 전체 기능을 테스트해보기 위해 main() 메서드를 수백 번 실행하는 수고가 필요하다.

// * 단위 테스트
// 테스트는 가능하면 작은 단위로 쪼개서 집중해서 할 수 있어야 한다.
// 관심사의 분리라는 원리가 여기에도 적용된다.
// 단위 테스트를 하는 이유는 개발자가 설계하고 만든 코드가 원래 의도한 대로 동작하는지를
// 개발자 스스로 빨리 확인받기 위해서다.

// * 자동수행 테스트 코드
// 테스트는 자동으로 수행되도록 코드로 만들어지는 것이 중요하다.
// 애플리케이션을 구성하는 클래스 안에 테스트 코드를 포함시키는 것보다는
// 별도로 테스트용 클래스를 만들어서 테스트 코드를 넣는 편이 낫다.
// 자동을 수행되는 테스트의 장점은 자주 반복될 수 있다는 것이다.

// * 테스트의 결과
// 모든 테스트는 성공과 실패의 두 가지 결과를 가질 수 있다.
// 또 테스트의 실패는 테스트가 진행되는 동안에 에러가 발생해서 실패한 경우와,
// 테스트 작업 중에 에러가 발생하진 않았지만 그 결과가 기대한 것과 다르게 나오는 경우로 구분해볼 수 있다.

// * JUnit
// JUnit은 자바에서 단위 테스트를 자동으로 작성·실행하게 해주는 표준 테스트 프레임워크다.
// 위 Start.java처럼 main()으로 직접 실행하고 콘솔을 눈으로 확인하던 방식을 대체한다.

// - 프레임워크의 특징: 제어의 역전(IoC)
//   main()은 "내가 직접" 테스트 흐름을 호출하지만,
//   JUnit에서는 우리가 테스트 메서드만 작성해두면 '프레임워크가 알아서' 그 메서드를 찾아 실행해준다.
//   (개발자가 흐름을 제어하는 게 아니라, 프레임워크가 개발자의 코드를 불러서 제어한다 → IoC)

// - 기본 사용법 (JUnit 5 기준)
//   · @Test           : 이 메서드가 테스트라는 표시. JUnit이 이 메서드들을 자동으로 찾아 실행한다.
//   · @BeforeEach     : 각 @Test 실행 '전'마다 매번 실행(공통 준비 작업, 예: 컨텍스트/객체 셋업).
//   · @AfterEach      : 각 @Test 실행 '후'마다 매번 실행(뒷정리).
//   · assertEquals(기대값, 실제값) 등 단언(assert) 메서드로 결과를 '코드로' 검증한다.
//     → 사람이 콘솔을 보고 판단할 필요가 없어진다(수동 확인의 번거로움 해결).
//     → 기대값과 다르면 테스트 실패로 자동 표시되고, 에러가 나도 실패로 잡힌다(테스트의 두 가지 결과).

// - 위 코드를 JUnit 테스트로 바꾸면 대략 이런 모습이 된다:
//     @Test
//     void getUser() throws Exception {
//         var context = new AnnotationConfigApplicationContext(DaoFactory.class);
//         UserDAO userDao = context.getBean("userDao", UserDAO.class);
//         User user = userDao.get("test1");
//         assertEquals("기대하는이름", user.getName());  // System.out.println 대신 단언으로 검증
//     }

public class Start {
    static void main(String[] args) throws SQLException, ClassNotFoundException {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);

        UserDAO userDAO = context.getBean("userDAO", UserDAO.class);
        User user = userDAO.get("test1");
        System.out.println("test2".equals(user.getName()));
    }
}










