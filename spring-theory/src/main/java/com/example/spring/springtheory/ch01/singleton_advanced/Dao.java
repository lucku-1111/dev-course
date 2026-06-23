package com.example.spring.springtheory.ch01.singleton_advanced;

interface ConnectionMaker {
    String makeConnection();
}

class SimpleConnectionMaker implements ConnectionMaker {
    private static final SimpleConnectionMaker instance = new SimpleConnectionMaker();

    private SimpleConnectionMaker() {}

    public static SimpleConnectionMaker getInstance() {
        return instance;
    }

    @Override
    public String makeConnection() {
        return "DB연결";
    }
}

class UserDao {
    private static final UserDao instance = new UserDao();

    private UserDao() {}

    public static UserDao getInstance() {
        return instance;
    }

    private ConnectionMaker connectionMaker = SimpleConnectionMaker.getInstance();

    String findUser(String userId) {
        return userId + " 조회 [" + connectionMaker.makeConnection() + "]";
    }
}

public class Dao {}
