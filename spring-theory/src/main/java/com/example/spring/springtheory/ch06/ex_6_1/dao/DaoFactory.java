package com.example.spring.springtheory.ch06.ex_6_1.dao;

import com.example.spring.springtheory.ch06.ex_6_1.service.TransactionHandler;
import com.example.spring.springtheory.ch06.ex_6_1.service.UserService;
import com.example.spring.springtheory.ch06.ex_6_1.service.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;

@Configuration // 애플리케이션 컨텍스트 또는 빈 팩토리가 사용할 설정 정보라는 표시
public class DaoFactory {

    @Bean
    public UserService userService() {
        TransactionHandler txHandler = new TransactionHandler(userServiceImpl(), transactionManager(), "upgrade");

        // * 다이나믹 프록시를 런타임에 생성
        // 모든 메서드 호출이 txHandler.invoke()로 전달된다.
        // "메모리에 흉내낼 클래스를 임시로 만들어서 메서드에 맞게 처리한다."
        //  - 메모리에 — .java/.class 파일로 디스크에 저장되는 게 아니라, JVM이 런타임에 바이트코드를 즉석 생성해서 메모리에 올린다.
        //  - 흉내낼 클래스 — UserService 인터페이스를 구현한 $Proxy0을 만든다. 그래서 UserService인 척할 수 있다.
        //  - 메서드에 맞게 처리 — 호출된 메서드 정보를 invoke()로 넘겨, 우리가 정한 대로 처리한다.
        // ** 프록시 자신은 "처리"를 안 한다
        // $Proxy0(프록시)은 처리 로직이 전혀 없다. 어떤 메서드가 불리든 무조건 invoke()로 떠넘기기만 한다.
        return (UserService) Proxy.newProxyInstance(
                getClass().getClassLoader(), // (1) 프록시 클래스를 적재할 클래스로더
                new Class[]{UserService.class}, // (2) 프록시가 구현(흉내)할 인터페이스
                txHandler // (3) 호출을 받아 처리할 핸들러
        );
    }

    @Bean
    public UserServiceImpl userServiceImpl() {
        return new UserServiceImpl(userDAO());
    }

    @Bean
    public UserDAO userDAO() {
        return new UserDAO(jdbcContext());
    }

    @Bean
    public JdbcContext jdbcContext() {
        return new JdbcContext(dataSource());
    }

    // 커넥션을 우리가 직접 만들던 SimpleConnectionMaker 대신, 스프링 표준 DataSource를 쓴다.
    //  - DriverManagerDataSource: 학습용 DataSource(요청마다 커넥션 생성). 운영은 커넥션 풀을 쓴다.
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/springtheory");
        dataSource.setUsername("root");
        dataSource.setPassword("6731");
        return dataSource;
    }

    // * 트랜잭션 추상화의 '실제 구현'을 여기서 결정한다.
    //  - 반환 타입은 추상화 인터페이스(PlatformTransactionManager).
    //  - JDBC를 쓰므로 DataSourceTransactionManager를 꽂는다.
    //    (JPA면 JpaTransactionManager, 분산이면 JtaTransactionManager로 '이 한 줄만' 바꾸면 된다.
    //     UserService 코드는 전혀 손대지 않는다 -> 이것이 추상화의 이득)
    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
