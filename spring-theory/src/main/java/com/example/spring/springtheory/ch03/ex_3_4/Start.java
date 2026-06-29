package com.example.spring.springtheory.ch03.ex_3_4;

// * 문제점
// UserDAO의 컨텍스트 메서드인 jdbcContextWithStatementStrategy()는
// PreparedStatement를 실행하는 기능을 가진 메서드에서 공유할 수 있다.
// 즉 다른 DAO에서도 사용이 가능하다.

// '클래스 분리'

public class Start {
    static void main() {

    }
}
