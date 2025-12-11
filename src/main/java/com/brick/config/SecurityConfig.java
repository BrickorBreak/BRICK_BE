package com.brick.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> {})
                .csrf(csrf -> csrf.disable())  //Talend 테스트용
                .authorizeHttpRequests(auth -> auth

//                        .requestMatchers("/api/v1/auth/**").permitAll() //회원가입/로그인은 인증 없이 허용

//                        .requestMatchers(
//                                "/api/v1/auth/**",
//                                "/swagger-ui/**",
//                                "/v3/api-docs/**",
//                                "/swagger-ui.html",
//                                "/api/v1/feeds/**",
//                                "/api/v1/comments/**",
//                                "/swagger-resources/**",
//                                "/api/v1/feeds/*/comments"
                        .anyRequest().permitAll()
                )
                .httpBasic(httpBasic -> httpBasic.disable())
                .formLogin(form -> form.disable());

        return http.build();
    }
}
