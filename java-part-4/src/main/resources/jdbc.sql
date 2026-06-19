CREATE DATABASE java_basic
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

# 삭제
# DROP DATABASE java_basic;

SHOW DATABASES;
USE java_basic;

SHOW TABLES;

CREATE TABLE IF NOT EXISTS member (
                                      id    INT          NOT NULL AUTO_INCREMENT,   -- PK, 자동 증가
                                      name  VARCHAR(50)  NOT NULL,                  -- 이름
    age   INT,                                    -- 나이
    phone VARCHAR(20),                            -- 전화번호
    PRIMARY KEY (id)
    );

DESC member;

SELECT id, name, age, phone FROM member;
SELECT * FROM member;

INSERT INTO member ( name, age, phone ) VALUES ( '홍길동', 20, '010-1234-5678' );
