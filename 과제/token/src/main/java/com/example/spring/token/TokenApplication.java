package com.example.spring.token;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class TokenApplication {

    public static void main(String[] args) {
        SpringApplication.run(TokenApplication.class, args);
    }

}
