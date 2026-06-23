package com.example.spring.springtheory.ch01.ex_1_6.dao;

import com.example.spring.springtheory.ch01.ex_1_1.domain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

// * 직접 만든 싱글톤(자바 코드로 구현하는 전통적인 싱글톤 패턴 - GoF)
// 만드는 순서
//  1) 클래스당 오브젝트를 1개만 담아둘 자기 자신 타입의 static 필드를 만든다.
//  2) 생성자를 private으로 막아 외부에서 new 로 마구 만들지 못하게 한다.
//  3) 유일한 오브젝트를 돌려주는 static 메서드(getInstance)를 둔다.
//     - 처음 호출될 때 한 번만 만들고, 그 다음부터는 이미 만든 오브젝트를 그대로 돌려준다.

public class UserDAO {

    private SimpleConnectionMaker simpleConnectionMaker;

    public UserDAO(SimpleConnectionMaker simpleConnectionMaker) {
        this.simpleConnectionMaker = simpleConnectionMaker;
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

    public User get(String id) throws ClassNotFoundException, SQLException {
        String query = "SELECT * FROM users WHERE id = ?";

        try (
                Connection conn = simpleConnectionMaker.makeNewConnection();
                PreparedStatement pstmt = conn.prepareStatement(query);
        ) {
            pstmt.setString(1, id);
            ResultSet resultSet = pstmt.executeQuery();

            resultSet.next();

            User user = new User();
            user.setId( resultSet.getString("id") );
            user.setName( resultSet.getString("name") );
            user.setPassword( resultSet.getString("password") );

            return user;
        }

    }

}
