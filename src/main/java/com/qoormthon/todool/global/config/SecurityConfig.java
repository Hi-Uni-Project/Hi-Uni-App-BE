package com.qoormthon.todool.global.config;

import com.qoormthon.todool.global.common.filter.JwtAuthenticationFilter;
import com.qoormthon.todool.global.common.security.CustomAccessDeniedHandler;
import com.qoormthon.todool.global.common.util.BaseUtil;
import com.qoormthon.todool.global.common.util.JWTutil;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

@Configuration
public class SecurityConfig {
    private final CustomAccessDeniedHandler accessDeniedHandler;

    @Autowired
    public SecurityConfig(CustomAccessDeniedHandler accessDeniedHandler) {
        this.accessDeniedHandler = accessDeniedHandler;
    }

    @Bean
    public SecretKey jwtSecretKey(@Value("${jwt.secret}") String secret) {
        return new SecretKeySpec(
                secret.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm()
        );
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JWTutil jwTutil, BaseUtil baseUtil) throws Exception {
        http
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(accessDeniedHandler)); //인가 예외처리

        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/swagger-resources/**",
                                "/test",
                                "/user/login",
                                "/user/signup",
                                "/user/check/id/**",
                                "/user/check/pwd/**",
                                "/images/**",
                                "/auth/refresh",
                                "/univ/find/**",
                                "/univ/find/major/**",
                                "/univ/find/all",
                                "/mail/valid",
                                "/mail/send",
                                "/chat-connect/**"
                        ).permitAll()
                        .requestMatchers(
                                "/chat/matching",
                                "/chat/matching/status/**",
                                "/chat/matching/cancel/**",
                                "/user/find/**"
                        ).hasAnyRole("USER", "ADMIN")
                        .anyRequest().hasRole("ADMIN"))
                .addFilterBefore(new JwtAuthenticationFilter(jwTutil, baseUtil), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }


}
