package com.Project.FraudGuard.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(httpSecurityCsrfConfigurer -> httpSecurityCsrfConfigurer.disable()) // For testing purposes; do not disable CSRF in production
                .authorizeRequests()// Allow access to /account/post without authentication
                .anyRequest().permitAll(); // Require authentication for all other endpoints

        return http.build();
    }
}
