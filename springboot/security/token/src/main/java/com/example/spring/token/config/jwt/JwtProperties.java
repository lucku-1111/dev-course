package com.example.spring.token.config.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    private String issuer;
    private String secretKey;
    private String accessTokenValidity;
    private String refreshTokenValidity;

}
