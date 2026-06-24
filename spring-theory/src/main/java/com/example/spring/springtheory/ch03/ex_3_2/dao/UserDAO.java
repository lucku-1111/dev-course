package com.example.spring.springtheory.ch03.ex_3_2.dao;


import com.example.spring.springtheory.ch03.ex_3_2.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// * 전략 패턴의 적용
// - 컨텍스트
// 변하지 않는 부분: JDBC 커넥션/실행/자원관리 공통 흐름
// - 전략
// 변하는 부분: 어떤 PreparedStatement를 만들지 -> 인터페이스로 추상화

// 컨텍스트는 '인터페이스(StatementStrategy)에만' 의존하고, 실제 전략은 런타임에 주입받는다.
// 그래서 새 기능을 추가해도 컨텍스트 코드는 닫혀 있고(수정X) 전략만 새로 만들면 된다(확장O) = OCP.

public class UserDAO {

    private SimpleConnectionMaker simpleConnectionMaker;

    public UserDAO(SimpleConnectionMaker simpleConnectionMaker) {
        this.simpleConnectionMaker = simpleConnectionMaker;
    }

    protected UserDAO() {
    }

    public void add(User user) throws ClassNotFoundException, SQLException {

        String query = "INSERT INTO users (id, name, password) VALUES (?, ?, ?)";

        try (
                Connection conn = simpleConnectionMaker.makeNewConnection();
                PreparedStatement pstmt = conn.prepareStatement(query);
                ) {
            pstmt.setString(1, user.getId());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getPassword());
            pstmt.executeUpdate();
        }

    }

    public void deleteAll() throws SQLException, ClassNotFoundException {
        try (
                Connection conn = simpleConnectionMaker.makeNewConnection();
                PreparedStatement pstmt = makeStatement(conn);
        ) {
            pstmt.executeUpdate();
        }
    }
}
