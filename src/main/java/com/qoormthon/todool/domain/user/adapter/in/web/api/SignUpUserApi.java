package com.qoormthon.todool.domain.user.adapter.in.web.api;

import com.qoormthon.todool.domain.user.adapter.dto.request.SignUpUserRequestDto;
import com.qoormthon.todool.domain.user.adapter.dto.response.SignUpUserResponseDto;
import com.qoormthon.todool.domain.user.application.port.in.SignUpUserUseCase;
import com.qoormthon.todool.domain.user.mapper.UserMapper;
import com.qoormthon.todool.global.common.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;

@RestController
@RequestMapping("/user")
@Tag(name = "유저 관련 api 입니다.", description = "로그인, 회원가입, 유저 정보 수정 등의 기능입니다.")
public class SignUpUserApi {

    private final SignUpUserUseCase signUpUserUseCase;
    private final UserMapper userMapper;

    @Autowired
    public SignUpUserApi(SignUpUserUseCase signUpUserUseCase, UserMapper userMapper) {
        this.signUpUserUseCase = signUpUserUseCase;
        this.userMapper = userMapper;
    }

    @ApiResponse(
            responseCode = "200",
            description = "### ✅ 회원가입에 성공한 경우",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            example = """
                                    {
                                       "status": "OK",
                                       "message": "회원가입에 성공하였습니다.",
                                       "data": "eyJhbGciOiJIUzI1NiJ9.eyJ0eXBlIjoiYWNjZXN..."
                                     }
                                    """

                    )
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "--- \n### ❌ 이미 등록된 사용자일 경우",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            example = """
                                    {
                                       "status": "BAD_REQUEST",
                                       "message": "이미 등록된 사용자입니다.",
                                       "data": null
                                     }
                                    """

                    )
            )
    )
    @Operation(summary = "회원가입 api", description = "이미지는 선택사항 입니다.")
    @PostMapping(
            value = "/signup",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public ResponseEntity<?> signUp(@RequestPart(value = "user") SignUpUserRequestDto signUpUserRequestDto
            , @RequestPart(value = "image", required = false) MultipartFile file) {
        SignUpUserResponseDto signUpUserResponseDto =
                signUpUserUseCase.signUp(userMapper.signUpUserRequestDtoToCommand(signUpUserRequestDto), file);

        ResponseCookie responseCookie = ResponseCookie.from("refreshToken", signUpUserResponseDto.getRefreshToken())
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .path("/auth")
                .maxAge(Duration.ofDays(7))
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(ResponseDto
                        .response(HttpStatus.OK, "회원가입에 성공하였습니다.", signUpUserResponseDto.getAccessToken()));
    }
}
