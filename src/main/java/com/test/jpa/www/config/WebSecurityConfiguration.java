package com.test.jpa.www.config;

import com.test.jpa.www.jwt.JwtConfigurationFilters;
import com.test.jpa.www.jwt.JwtConfigurationToken;
import com.test.jpa.www.jwt.JwtTools;
import com.test.jpa.www.service.OAuth2UserServiceImpl;
import com.test.jpa.www.tools.OAuth2LoginSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.SessionManagementConfigurer;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.bind.annotation.CrossOrigin;

@Configuration
@EnableWebSecurity
@CrossOrigin
public class WebSecurityConfiguration {
    private final MyBasicAuthEntryPoint myBasicAuthEntryPoint;
    private final JwtConfigurationToken jwtConfigurationToken;
    private final JwtTools jwtTools;
    private final OAuth2UserServiceImpl oAuth2UserService;
    private final OAuth2LoginSuccessHandler successHandler;

    @Autowired
    public WebSecurityConfiguration(MyBasicAuthEntryPoint myBasicAuthEntryPoint,
                                    JwtConfigurationToken jwtConfigurationToken,
                                    JwtTools jwtTools,
                                    OAuth2UserServiceImpl oAuth2UserService,
                                    OAuth2LoginSuccessHandler successHandler) {
        this.myBasicAuthEntryPoint = myBasicAuthEntryPoint;
        this.jwtConfigurationToken = jwtConfigurationToken;
        this.jwtTools = jwtTools;
        this.oAuth2UserService = oAuth2UserService;
        this.successHandler = successHandler;
    }

    @Bean
    public Pbkdf2PasswordEncoder getEncoder() {
        return new Pbkdf2PasswordEncoder("secret", 64, 400000, Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512);
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity https) throws Exception {
        https
                .csrf().disable()
                .cors().disable()
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/icon/*", "/images/**", "/css/**", "/fonts/**", "/scripts/**").permitAll()
                        .requestMatchers("/login", "/registration", "/home", "/activated/*", "/api/user/auth/*", "/oauth2/**").permitAll()
                        .requestMatchers("/hello").authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/api/user/")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/api/user/{id}")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/api/user/NSP")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/api/user/Email")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/api/user/Gender")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/api/user/Phone")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/api/user/Age")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/api/user/Enable")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/api/user/Save")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/api/user/Update")).hasRole("ADMIN")
                        .requestMatchers(new AntPathRequestMatcher("/api/user/Delete")).hasRole("ADMIN")
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/hello")
                        .failureForwardUrl("/login")
                )
                .logout().logoutUrl("/logout").permitAll()
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .and()
                .apply(new JwtConfigurationFilters(jwtConfigurationToken, jwtTools))
                .and()
                .httpBasic().authenticationEntryPoint(myBasicAuthEntryPoint)
                .and()
                .oauth2Client()
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint().userService(oAuth2UserService)
                .and()
                .successHandler(successHandler)
                .and()
                .sessionManagement((session) -> session
                        .sessionFixation(SessionManagementConfigurer.SessionFixationConfigurer::newSession)
                        .maximumSessions(1)
                );
        return https.build();
    }
}