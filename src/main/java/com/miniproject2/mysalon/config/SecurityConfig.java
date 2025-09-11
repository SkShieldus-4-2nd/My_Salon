package com.miniproject2.mysalon.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;


/*@Configuration
public class SecurityConfig {

    // BCryptPasswordEncoder Bean
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
*//*
    // Security 설정
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf().disable() // REST API 테스트용
            .authorizeHttpRequests()
            .requestMatchers("/api/users/**").permitAll() // 회원 관련 API 인증 없이 허용
            .anyRequest().authenticated() // 그 외 요청은 인증 필요
            .and()
            .httpBasic(); // 간단한 테스트용 Basic 인증 활성화

        return http.build();
    }*//*
}*/

