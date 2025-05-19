package com.qoormthon.todool.domain.user.adapter.in.web.api;

import com.qoormthon.todool.domain.user.adapter.dto.response.UserFindResponseDto;
import com.qoormthon.todool.domain.user.application.port.in.FindUserUseCase;
import com.qoormthon.todool.global.common.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "유저 관련 api 입니다.", description = "로그인, 회원가입, 유저 정보 수정 등의 기능입니다.")
public class FindUserApi {

    private final FindUserUseCase FindUserUseCase;

    @Autowired
    public FindUserApi(FindUserUseCase FIndUserUseCase) {
        this.FindUserUseCase = FIndUserUseCase;
    }

    @Operation(summary = "유저정보 조회 api", description = "유저 아이디로 유저 정보를 조회합니다.")
    @GetMapping("/find/{userId}")
    public ResponseEntity<?> UserFind(@PathVariable String userId, HttpServletRequest request) {
        UserFindResponseDto userFindResponseDto = FindUserUseCase.userFind(userId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.response(HttpStatus.OK, "유저 정보 조회 성공", userFindResponseDto));
    }

}
