package com.sicredi.todo.config;

import com.sicredi.todo.repository.UserRepository;
import com.sicredi.todo.security.filter.RecognizeUserFromJwtOnEveryIncomingRequestFilter;
import com.sicredi.todo.security.handler.RespondWith401WhenTheRequestHasNoValidToken;
import com.sicredi.todo.service.JwtService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import tools.jackson.databind.ObjectMapper;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    public SecurityConfig(JwtService jwtService, UserRepository userRepository, ObjectMapper objectMapper) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/auth/register", "/auth/login").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(respondWith401WhenTheRequestHasNoValidToken()))
                .addFilterBefore(
                        recognizeUserFromJwtOnEveryIncomingRequestFilter(),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private RecognizeUserFromJwtOnEveryIncomingRequestFilter recognizeUserFromJwtOnEveryIncomingRequestFilter() {
        return new RecognizeUserFromJwtOnEveryIncomingRequestFilter(jwtService, userRepository);
    }

    private RespondWith401WhenTheRequestHasNoValidToken respondWith401WhenTheRequestHasNoValidToken() {
        return new RespondWith401WhenTheRequestHasNoValidToken(objectMapper);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
