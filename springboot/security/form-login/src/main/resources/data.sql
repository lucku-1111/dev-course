-- user 테이블 생성
CREATE TABLE user (
      id BIGINT NOT NULL AUTO_INCREMENT,
      name VARCHAR(20),
      email VARCHAR(50),
      user_id VARCHAR(50),
      password VARCHAR(100),
      role ENUM('ROLE_USER', 'ROLE_ADMIN') DEFAULT 'ROLE_USER',
      PRIMARY KEY (id)
);