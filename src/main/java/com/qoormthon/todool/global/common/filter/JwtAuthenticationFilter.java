package com.qoormthon.todool.global.common.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qoormthon.todool.global.common.response.ResponseDto;
import com.qoormthon.todool.global.common.util.BaseUtil;
import com.qoormthon.todool.global.common.util.JWTutil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private JWTutil jwTutil;
    private BaseUtil baseUtil;

    public JwtAuthenticationFilter(JWTutil jwTutil, BaseUtil baseUtil) {
        this.jwTutil = jwTutil;
        this.baseUtil = baseUtil;
    }

    private void handleAuthenticationError(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json;charset=UTF-8");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(ResponseDto.response(HttpStatus.UNAUTHORIZED, "인증이 거부되었습니다.", null));
        response.getWriter().write(jsonResponse);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            log.info("[" + baseUtil.getClientIpv4(request) + "]" + request.getMethod() + ":" + request.getRequestURI() + " 요청이 들어왔습니다.");

            String token = jwTutil.extractToken(request);

            if (token == null) {
                filterChain.doFilter(request, response);
                log.error("인증이 거부되었습니다.");
                return;
            }

            String userId = jwTutil.getUserId(token);
            String role = jwTutil.getRole(token);

            if (jwTutil.isExpired(token)) {
                log.error("유효기간이 만료된 토큰입니다.");
                handleAuthenticationError(response);
                return;
            }

            List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
            User authenticatedUser = new User(userId.toString(), "", authorities);

            var authenticationToken = new UsernamePasswordAuthenticationToken(authenticatedUser, null, authorities);
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("유효하지 않는 토큰입니다.");
            handleAuthenticationError(response);
        }
    }

}
