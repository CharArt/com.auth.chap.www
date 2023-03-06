package com.test.jpa.www.jwt;

import com.test.jpa.www.entity.Users;
import com.test.jpa.www.exception.JwtAuthenticationException;
import com.test.jpa.www.service.UserService;
import com.test.jpa.www.tools.TimeExpiration;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTools {

    private final UserService service;
    private final JwtSecretKey secretKey;
    private final JwtConfigurationToken config;

    private final Logger logger = LoggerFactory.getLogger(JwtTools.class);

    private final Date timeValidityAccessToken = new Date(TimeExpiration.FIFTEEN_MINUTES.getTime() + new Date().getTime());
    private final Date timeValidityRefreshToken = new Date(TimeExpiration.THIRTY_MINUTES.getTime() + new Date().getTime());

    @Autowired
    public JwtTools(JwtSecretKey secretKey, UserService service, JwtConfigurationToken config) {
        this.secretKey = secretKey;
        this.service = service;
        this.config = config;
    }

    public String getAccessToken(Authentication authResult) {
        return Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .claim("time", timeValidityAccessToken)
                .setIssuedAt(timeValidityAccessToken)
                .signWith(secretKey.secretKey())
                .compact();
    }

    public String getAccessToken(Users user) {
        return Jwts.builder()
                .setSubject(user.getLogin())
                .claim("authorities", user.getAuthorities())
                .claim("time", timeValidityAccessToken)
                .setIssuedAt(timeValidityAccessToken)
                .signWith(secretKey.secretKey())
                .compact();
    }

    public String getRefreshToken(Authentication authResult) {
        return Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .claim("time", timeValidityRefreshToken)
                .setIssuedAt(timeValidityRefreshToken)
                .signWith(secretKey.secretKey())
                .compact();
    }

    public String getRefreshToken(Users user) {
        return Jwts.builder()
                .setSubject(user.getLogin())
                .claim("authorities", user.getAuthorities())
                .claim("time", timeValidityRefreshToken)
                .setIssuedAt(timeValidityRefreshToken)
                .signWith(secretKey.secretKey())
                .compact();
    }

    public boolean validityRefreshTokenTime(String token) {
        logger.info("Start method ValidityRefreshTokenTime");
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey.secretKey()).build().parseClaimsJws(token);
        if (!claims.getBody().getIssuedAt().before(new Date())) {
            return true;
        }
        logger.error("Method ValidityRefreshTokenTime finished with error token is rotten");
        return false;
    }

    public boolean validityAccessTokenTime(String token) {
        logger.info("Start method ValidityAccessTokenTime");
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(secretKey.secretKey()).build().parseClaimsJws(token);
        if (!claims.getBody().getIssuedAt().before(new Date())) {
            return true;
        }
        logger.error("Method ValidityAccessTokenTime finished with error token is rotten");
        return false;
    }

    public String getLoginFormToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey.secretKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

    public Authentication validityToken(HttpServletRequest request) {
        String authorizationToken = request.getHeader(config.getAuthorizationHeader());
        String token = authorizationToken.replace(config.getTokenPrefix(), "");
        if (validityAccessTokenTime(token)) {
            UserDetails user = service.loadUserByUsername(getLoginFormToken(token));
            return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
        } else {
            throw new JwtAuthenticationException("yours token rotten");
        }
    }
}