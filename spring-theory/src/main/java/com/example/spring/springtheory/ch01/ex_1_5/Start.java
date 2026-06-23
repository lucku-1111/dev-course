package com.example.spring.springtheory.ch01.ex_1_5;

import com.example.spring.springtheory.ch01.ex_1_4.dao.DaoFactory;
import com.example.spring.springtheory.ch01.ex_1_4.dao.UserDAO;

// * 스프링의 제어의 역전(IoC, Inversion of Control)
// 이제 DaoFactory를 스프링에서 사용이 가능하도록 변신시켜보자.
// 스프링에서는 스프링이 제어권을 가지고 직접 만들고 관계를 부여하는 오브젝트를 빈(Bean)이라고 부른다.
// 자바빈은 오브젝트 단위의 애플리케이션 컴포넌트를 의미하고,
// 동시에 스프링 빈은 컨테이너가 생성과 관계설정, 사용 등을 제어해주는 제어의 역전이 적용된 오브젝트를 가리키는 말이다.
// 스프링에서는 빈의 생성과 관계설정 같은 제어를 담당하는 IoC 오브젝트를 빈 팩토리라고한다.
// 정확히는 빈팩토리보다는 이를 좀 더 확장한 '애플리케이션 컨텍스트'를 주로 사용한다.
// 애플리케이션 컨텍스트는 IoC 방식을 따라 만들어진 일종의 빈 팩토리라고 생각하면된다.

// * 컴포넌트(Component)
// 컴포넌트란 애플리케이션을 구성하는, 독립적으로 갈아 끼울 수 있는 부품 같은 오브젝트를 말한다.
// 우리 예제에서는 실제 일을 하는 UserDAO, DConnectionMaker 같은 오브젝트가 컴포넌트에 해당한다.
// 컴포넌트의 핵심은 "자신이 할 일(비즈니스 로직)에만 집중하고,
//  자신이 어떻게 만들어지는지, 누구와 연결되는지는 신경 쓰지 않는다"는 점이다.
// 즉 UserDAO는 "DB에 사용자를 넣고 빼는 일"만 알면 되고,
//  어떤 ConnectionMaker 구현체와 연결될지는 외부에 맡긴다.
// 이렇게 만들어진 컴포넌트는 코드 수정 없이 다른 컴포넌트로 교체하거나 재사용할 수 있다.
//  (예: DConnectionMaker -> NConnectionMaker 로 교체해도 UserDAO 코드는 그대로다.)

// * 컨테이너(Container)
// 컨테이너란 이 컴포너들을 담아두고, 생성하고, 서로 연결(관계설정)해주고,
// 생명주기(언제 만들고 언제 없앨지)까지 관리해주는 실행 환경을 말한다.
// 컴포넌트가 '부품'이라면, 컨테이너는 그 부품들을 조립하고 가동시키는 '공장'인 셈이다.


public class Start {
    static void main(String[] args) {
        UserDAO dao = new DaoFactory().userDAO();
    }
}










