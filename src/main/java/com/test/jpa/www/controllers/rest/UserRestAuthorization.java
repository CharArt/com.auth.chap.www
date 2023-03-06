package com.test.jpa.www.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.jpa.www.dto.AuthenticationUserDTO;
import com.test.jpa.www.entity.Users;
import com.test.jpa.www.jwt.JwtConfigurationToken;
import com.test.jpa.www.jwt.JwtTools;
import com.test.jpa.www.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/user/auth")
public class UserRestAuthorization {

    private final UserService service;
    private final JwtTools tools;
    private final JwtConfigurationToken config;
    private final AuthenticationManager authenticationManager;

    public UserRestAuthorization(UserService service,
                                 JwtTools tools,
                                 JwtConfigurationToken config,
                                 AuthenticationManager authenticationManager) {
        this.service = service;
        this.tools = tools;
        this.config = config;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/refresh")
    public ResponseEntity<String> refresh(HttpServletRequest request, HttpServletResponse response) {

        String authorizationToken = request.getHeader(config.getRefreshTokenHeader());
        String token = authorizationToken.replace(config.getTokenPrefix(), "");
        if (tools.validityRefreshTokenTime(token)) {
            Users users = service.findUserByLogin(tools.getLoginFormToken(token));
            response.addHeader(config.getAccessTokenHeader(), config.getTokenPrefix() + tools.getAccessToken(users));

            response.addHeader(config.getRefreshTokenHeader(), config.getTokenPrefix() + tools.getRefreshToken(users));
            return new ResponseEntity<String>("200", HttpStatus.OK);
        }
        return new ResponseEntity<String>("Yours token rotten", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(HttpServletRequest request, HttpServletResponse response) throws IOException {
        AuthenticationUserDTO userDTO = new ObjectMapper().readValue(request.getInputStream(), AuthenticationUserDTO.class);
        Authentication req = new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword());
        Authentication res = authenticationManager.authenticate(req);
        if (res.isAuthenticated()) {
            Users users = service.findUserByLogin(userDTO.getUsername());
            response.addHeader(config.getAccessTokenHeader(), config.getTokenPrefix() + tools.getAccessToken(users));
            response.addHeader(config.getRefreshTokenHeader(), config.getTokenPrefix() + tools.getRefreshToken(users));
            return new ResponseEntity<String>("200", HttpStatus.OK);
        } else {
            return new ResponseEntity<String>("You aren't authenticated!", HttpStatus.BAD_REQUEST);
        }
    }
}