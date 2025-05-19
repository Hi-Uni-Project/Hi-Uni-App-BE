package com.qoormthon.todool.domain.user.adapter.in.web.api;

import com.qoormthon.todool.domain.user.adapter.dto.response.CheckUserIdResponseDto;
import com.qoormthon.todool.domain.user.adapter.dto.response.CheckUserPwdResponseDto;
import com.qoormthon.todool.domain.user.application.port.in.CheckUserIdUseCase;
import com.qoormthon.todool.domain.user.application.port.in.CheckUserPwdUseCase;
import com.qoormthon.todool.global.common.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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
public class UserCredentialsCheckApi {

    private final CheckUserIdUseCase checkUserIdUseCase;
    private final CheckUserPwdUseCase checkUserPwdUseCase;

    @Autowired
    public UserCredentialsCheckApi(CheckUserIdUseCase checkUserIdUseCase, CheckUserPwdUseCase checkUserPwdUseCase) {
        this.checkUserIdUseCase = checkUserIdUseCase;
        this.checkUserPwdUseCase = checkUserPwdUseCase;
    }



    @ApiResponse(
            responseCode = "200",
            description = "### ✅ 사용 가능한 아이디일 경우",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            example = """
                                    {
                                       "status": "OK",
                                       "message": "사용 가능한 id 입니다.",
                                       "data": true
                                    }
                                    """

                    )
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "--- \n### ❌ 사용 불가능한 아이디일 경우",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            example = """
                                    {
                                           "status": "BAD_REQUEST",
                                           "message": "영문 소문자만 가능합니다.",
                                           "data": false
                                    }
                                    """

                    )
            )
    )
    @Operation(summary = "유저 id 유효성 체크 api", description = "현재 유저 아이디가 사용 가능한지 체크합니다.")
    @GetMapping("/check/id/{userId}")
    public ResponseEntity<?> checkUserId(@PathVariable String userId) {
        CheckUserIdResponseDto checkUserIdResponseDto = checkUserIdUseCase.checkUserId(userId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.response(HttpStatus.OK, checkUserIdResponseDto.getMessage(), checkUserIdResponseDto.isValid()));
    }


    @ApiResponse(
            responseCode = "200",
            description = "### ✅ 사용 가능한 비밀번호일 경우",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            example = """
                                    {
                                       "status": "OK",
                                       "message": "사용 가능한 비밀번호 입니다.",
                                       "data": true
                                    }
                                    """

                    )
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "--- \n### ❌ 사용 불가능한 비밀번호일 경우",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            example = """
                                    {
                                       "status": "BAD_REQUEST",
                                       "message": "한글은 포함될 수 없습니다.",
                                       "data": false
                                    }
                                    """

                    )
            )
    )
    @Operation(summary = "유저 pwd 유효성 체크 api", description = "현재 유저 패스워드가 사용 가능한지 체크합니다.")
    @GetMapping("/check/pwd/{userPwd}")
    public ResponseEntity<?> checkUserPwd(@PathVariable String userPwd) {
        CheckUserPwdResponseDto checkUserPwdResponseDto = checkUserPwdUseCase.checkUserPwd(userPwd);
        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.response(HttpStatus.OK, checkUserPwdResponseDto.getMessage(), checkUserPwdResponseDto.isValid()));
    }

}
