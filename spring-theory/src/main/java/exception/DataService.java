package exception;

import java.sql.SQLException;

public class DataService {
    private final FileLogger logger;

    public DataService(FileLogger logger) {
        this.logger = logger;
    }

    public String fetchWithRetry(FlakyService flaky) {
        int maxRetry = 3;

        for (int attempt = 1; attempt <= maxRetry; attempt++) {
            try {
                String result = flaky.fetch();
                logger.log("INFO", attempt + "번째 시도 성공: " + result);
                return result;
            } catch (SQLException e) {
                logger.log("WARN", attempt + "번째 시도 실패: " + e.getMessage());
            }
        }
        logger.log("ERROR", "재시도 " + maxRetry + "회 모두 실패");
        throw new RuntimeException("재시도 " + maxRetry + "회 모두 실패했습니다.");
    }

    public void avoidByThrows(FlakyService flaky) throws SQLException {
        flaky.fetch();
    }

    public void avoidByRethrow(FlakyService flaky) throws SQLException {
        try {
            flaky.fetch();
        } catch (SQLException e) {
            logger.log("WARN", "회피: 여기서 처리하지 않고 호출자에게 넘김 - " + e.getMessage());
            throw e;
        }
    }

    void registerUser(String id) {
        try {
            insertUser(id);
        } catch (SQLException e) {
            if ("23000".equals(e.getSQLState())) {
                logger.log("ERROR", "아이디 중복: " + id);
                throw new DuplicateUserIdException(id, e);
            }
            logger.log("ERROR", "회원 저장 중 DB 오류: " + id);
            throw new RuntimeException("회원 저장 중 DB 오류", e);
        }
    }

    void insertUser(String id) throws SQLException {
        throw new SQLException("Duplicate entry", "23000");
    }

    static class DuplicateUserIdException extends RuntimeException {
        DuplicateUserIdException(String id, Throwable cause) {
            super("이미 존재하는 아이디입니다: " + id, cause);
        }
    }
}
