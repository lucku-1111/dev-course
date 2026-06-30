package com.example.spring.springtheory.ch05.ex_5_3.dao;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

// * 트랜잭션에 '참여'할 수 있게 바뀐 JdbcContext
// ex_5_1: '매번 새 커넥션'을 만들고 try-with-resource로 바로 받았다.
// -> 메서드마다 커넥션이 다르므로, 여러 DAO호출을 '하나의 트랜잭션'으로 묶을 수 없다.
// ex_5_2: 스프링 DataSource + DataSourceUtils를 쓴다.
//      · DataSourceUtils.getConnection(dataSource)
//        -> 현재 진행 중인 트랜잭션이 있으면 '그 트랜잭션에 묶인 커넥션'을 돌려준다.
//           (이것이 '트랜잭션 동기화'다: 커넥션을 메서드 파라미터로 넘기지 않아도 공유된다)
//        -> 트랜잭션이 없으면 새 커넥션을 준다.
//      · DataSourceUtils.releaseConnection(conn, dataSource)
//        -> 트랜잭션에 묶인 커넥션이면 '닫지 않는다'(트랜잭션이 끝날 때 닫는다).
//           트랜잭션 밖이면 실제로 닫는다.
//  => 그래서 커넥션을 try-with-resources로 직접 닫으면 안 된다(트랜잭션이 깨진다). finally에서 release.
// 어떤 일련의 작업이 하나의 트랜잭션으로 묶이려면 그 작업이 진행되는 동안 DB 커넥션도 하나만 사용돼야 한다.

public class JdbcContext {

    private DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void workWithStatementStrategy(StatementStrategy statementStrategy) throws SQLException, ClassNotFoundException {
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try (
                PreparedStatement pstmt = statementStrategy.makeStatement(conn); // 변하는 부분을 전략에 위임
        ) {
            pstmt.executeUpdate();
        } finally {
            DataSourceUtils.releaseConnection(conn, dataSource); // 트랜젝션 커넥션이면 닫지 않음
        }
    }

    // 조회용 컨텍스트: 여러 건을 조회해 리스트로 돌려준다.
    // - 제네릭 타입 T는 사용하기 전에 반드시 어딘가에서 선언해야 한다.
    // 1) 클래스 레벨 선언
    // 2) 메서드 레벨 선언
    //  - 변하지 않는 흐름: 커넥션 획득 -> executeQuery -> ResultSet을 한 줄씩 순회 -> 자원 정리.
    //  - 변하는 부분 둘: '어떤 SELECT인가(strategy)'와 '한 줄을 무엇으로 만들까(rowMapper)'.
    public <T> List<T> query(StatementStrategy strategy, RowMapper<T> rowMapper) throws SQLException, ClassNotFoundException {
        Connection conn = DataSourceUtils.getConnection(dataSource);
        try (
                PreparedStatement pstmt = strategy.makeStatement(conn);
                ResultSet rs = pstmt.executeQuery();
        ) {
            List<T> results = new ArrayList<>();
            while (rs.next()) {
                results.add(rowMapper.mapRow(rs));
            }

            return results;
        } finally {
            DataSourceUtils.releaseConnection(conn, dataSource);
        }
    }

    // 조회용 컨텍스트: 정확히 한 건을 조회한다.
    // - 결과가 없으면 의미 있는 예(EmptyResultDataAccessException, ch04에서 배운 추상화 예외)를 던진다.
    public <T> T queryForObject(StatementStrategy strategy, RowMapper<T> rowMapper) throws SQLException, ClassNotFoundException {
        List<T> results = query(strategy, rowMapper);

        if (results.isEmpty()) {
            throw new EmptyResultDataAccessException(1); // 기대한 1건이 없음
        }

        return results.get(0);
    }
}
