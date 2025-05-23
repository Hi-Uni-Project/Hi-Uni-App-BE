package com.qoormthon.todool.domain.user.adapter.in.web.api;

import com.qoormthon.todool.domain.user.adapter.dto.request.LoginUserRequestDto;
import com.qoormthon.todool.domain.user.adapter.dto.response.LoginUserResponseDto;
import com.qoormthon.todool.domain.user.application.port.in.LoginUserUseCase;
import com.qoormthon.todool.domain.user.mapper.UserMapper;
import com.qoormthon.todool.global.common.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;

@RestController
@RequestMapping("/user")
@Tag(name = "유저 관련 api 입니다.", description = "로그인, 회원가입, 유저 정보 수정 등의 기능입니다.")
public class LoginUserApi {

    private final LoginUserUseCase loginUserUseCase;
    private final UserMapper userMapper;

    public LoginUserApi(LoginUserUseCase loginUserUseCase, UserMapper userMapper) {
        this.loginUserUseCase = loginUserUseCase;
        this.userMapper = userMapper;
    }

    @ApiResponse(
            responseCode = "200",
            description = "### ✅ 로그인에 성공한 경우",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            example = """
                                    {
                                       "status": "OK",
                                       "message": "로그인 성공",
                                       "data": "eyJhbGciOiJIUzI1NiJ9.eyJ0eXBlIjoiYWNjZXN..."
                                    }
                                    """

                    )
            )
    )
    @ApiResponse(
            responseCode = "401",
            description = "--- \n### ❌ 로그인에 실패한 경우",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            example = """
                                    {
                                        "status": "UNAUTHORIZED",
                                        "message": "아이디 또는 비밀번호가 올바르지 않습니다.",
                                        "data": false
                                    }
                                    """

                    )
            )
    )
    @Operation(summary = "로그인 api", description = "유저 로그인 api 입니다. jwt 토큰을 발급합니다.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginUserRequestDto loginUserRequestDto){
        LoginUserResponseDto loginUserResponseDto =
                loginUserUseCase.login(userMapper.loginUserRequestDtoToCommand(loginUserRequestDto));

        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", loginUserResponseDto.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/auth")
                .maxAge(Duration.ofDays(7))
                .build();

        return ResponseEntity.status(HttpStatus.OK)
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(ResponseDto.response(HttpStatus.OK, "로그인 성공", loginUserResponseDto.getAccessToken()));
    }
}
