package com.test.jpa.www.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;

@Configuration
public class JwtSecretKey {

    private final JwtConfigurationToken config;

    @Autowired
    public JwtSecretKey(JwtConfigurationToken config) {
        this.config = config;
    }

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(config.getSecretKey().getBytes());
    }
}
