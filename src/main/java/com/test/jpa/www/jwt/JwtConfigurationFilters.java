package com.test.jpa.www.jwt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

public class JwtConfigurationFilters extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

    private final JwtConfigurationToken config;
    private final JwtTools tools;

    @Autowired
    public JwtConfigurationFilters(JwtConfigurationToken config, JwtTools tools) {
        this.config = config;
        this.tools = tools;
    }

    @Override
    public void configure(HttpSecurity builder) throws Exception {
        AuthenticationManager manager = builder.getSharedObject(AuthenticationManager.class);
        builder.addFilter(new JwtUsernameAndPasswordAuthenticationFilter(manager, config, tools));
        builder.addFilterAfter(new JwtTokenFilter(tools), JwtUsernameAndPasswordAuthenticationFilter.class);
    }
}
