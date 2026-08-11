package com.sicredi.todo.config;

import com.sicredi.todo.repository.UserRepository;
import com.sicredi.todo.security.RecognizeUserFromJwtOnEveryIncomingRequestFilter;
import com.sicredi.todo.service.JwtService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserRepository userRepository;

    public SecurityConfig(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll())
                .addFilterBefore(
                        recognizeUserFromJwtOnEveryIncomingRequestFilter(),
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    private RecognizeUserFromJwtOnEveryIncomingRequestFilter recognizeUserFromJwtOnEveryIncomingRequestFilter() {
        return new RecognizeUserFromJwtOnEveryIncomingRequestFilter(jwtService, userRepository);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
