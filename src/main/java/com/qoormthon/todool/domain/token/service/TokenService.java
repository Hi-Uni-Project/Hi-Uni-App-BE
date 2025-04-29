package com.qoormthon.todool.domain.token.service;

import com.qoormthon.todool.global.common.response.ResponseDto;
import com.qoormthon.todool.global.common.util.JWTutil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;

@Service
@Slf4j
public class TokenService {
    @Autowired
    private JWTutil jwTutil;

    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        try {
            String refreshToken = this.getRefreshToken(request);
            if(refreshToken == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ResponseDto.response(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 존재하지 않습니다.", null));
            } else {
                if(jwTutil.getType(refreshToken).equals("access")) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                            .body(ResponseDto.response(HttpStatus.UNAUTHORIZED, "리프레시 토큰이 아닙니다.", null));
                }
                String token = jwTutil.createAccessToken(jwTutil.getUserId(refreshToken), jwTutil.getRole(refreshToken));
                return ResponseEntity.ok()
                        .header("Authorization", "Bearer " + token)
                        .body(ResponseDto.response(HttpStatus.OK, "토큰이 갱신되었습니다.", null));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ResponseDto.response(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다.", null));
        }
    }

    public String getRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if("refreshToken".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        } else {
            return null;
        }
        return null;
    }
}
