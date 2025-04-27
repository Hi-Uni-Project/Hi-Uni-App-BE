package com.qoormthon.todool.domain.token.controller;

import com.qoormthon.todool.domain.token.service.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "토큰 관련 api 입니다.", description = "jwt 토큰 재발급 기능입니다.")
public class TokenController {

    @Autowired
    private TokenService tokenService;

    @Operation(summary = "토큰 재발급 요청 api 입니다.", description = "jwt 토큰을 재발급 합니다.")
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        return tokenService.refreshToken(request);
    }


}
