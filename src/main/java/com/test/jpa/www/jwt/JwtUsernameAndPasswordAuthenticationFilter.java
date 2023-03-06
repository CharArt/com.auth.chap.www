package com.test.jpa.www.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.jpa.www.dto.AuthenticationUserDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtConfigurationToken config;
    private final JwtTools tools;

    @Autowired
    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfigurationToken config, JwtTools tools) {
        this.authenticationManager = authenticationManager;
        this.config = config;
        this.tools = tools;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AuthenticationUserDTO userDTO = new ObjectMapper().readValue(request.getInputStream(), AuthenticationUserDTO.class);
            Authentication authentication = new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());
            return this.authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        response.addHeader(config.getAccessTokenHeader(), config.getTokenPrefix() + tools.getAccessToken(authResult));
        response.addHeader(config.getRefreshTokenHeader(), config.getTokenPrefix() + tools.getRefreshToken(authResult));
    }
}
