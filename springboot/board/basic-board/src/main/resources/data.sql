CREATE DATABASE java_basic
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

-- member table
CREATE TABLE member (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(50) NOT NULL,
    password VARCHAR(50) NOT NULL,
    user_name VARCHAR(20) NOT NULL
);

-- board table
CREATE TABLE board (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(200) NOT NULL,
    content TEXT NOT NULL,
    user_id VARCHAR(50) NOT NULL,
    file_path VARCHAR(255),
    created DATETIME DEFAULT CURRENT_TIMESTAMP
);