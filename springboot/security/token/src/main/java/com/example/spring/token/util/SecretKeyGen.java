package com.example.spring.token.util;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGen {

    private static final int KEY_LENGTH_BYTES = 64;

    static void main(String[] args) {
        byte[] bytes = new byte[KEY_LENGTH_BYTES];

        new SecureRandom().nextBytes(bytes);

        System.out.println(Base64.getEncoder().encodeToString(bytes));
    }
}
