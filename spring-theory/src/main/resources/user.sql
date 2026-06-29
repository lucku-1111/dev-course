CREATE DATABASE IF NOT EXISTS springtheory
    DEFAULT CHARACTER SET utf8mb4
    DEFAULT COLLATE utf8mb4_general_ci;

CREATE TABLE users (
                       id VARCHAR(20) PRIMARY KEY,
                       name VARCHAR(20) NOT NULL,
                       password VARCHAR(20) NOT NULL
);

ALTER TABLE users
    ADD COLUMN level    INT NOT NULL DEFAULT 1,   -- 1=BASIC, 2=SILVER, 3=GOLD
    ADD COLUMN login    INT NOT NULL DEFAULT 0,   -- 로그인 횟수
    ADD COLUMN recommend INT NOT NULL DEFAULT 0;  -- 추천 수