package com.qoormthon.todool.domain.user.adapter.in.web.api;

import com.qoormthon.todool.domain.user.adapter.dto.response.FindUserResponseDto;
import com.qoormthon.todool.domain.user.application.port.in.FindUserUseCase;
import com.qoormthon.todool.global.common.response.ResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
@Tag(name = "유저 관련 api 입니다.", description = "로그인, 회원가입, 유저 정보 수정 등의 기능입니다.")
public class FindUserApi {

    private final FindUserUseCase findUserUseCase;

    @Autowired
    public FindUserApi(FindUserUseCase findUserUseCase) {
        this.findUserUseCase = findUserUseCase;
    }

    @ApiResponse(
            responseCode = "200",
            description = "### ✅ 유저가 존재할 경우",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            example = """
                                    {
                                       "status": "OK",
                                       "message": "유저 정보 조회 성공",
                                       "data": {
                                         "userId": "test123",
                                         "stdNo": "20230101",
                                         "nickName": "guest",
                                         "univName": "하이유니대학교",
                                         "firstMajor": "시각디자인학과",
                                         "secondMajor": "컴퓨터공학과",
                                         "mbti": "INTJ",
                                         "userInterests": [
                                           20,
                                           4,
                                           5
                                         ],
                                         "imageUrl": "https://example.com/image.jpg"
                                       }
                                     }
                                    """

                    )
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "--- \n### ❌ 유저가 존재하지 않을 경우",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            example = """
                                    {
                                           "status": "BAD_REQUEST",
                                           "message": "유저가 존재하지 않습니다.",
                                           "data": null
                                    }
                                    """

                    )
            )
    )
    @Operation(summary = "유저정보 조회 api", description = "유저 아이디로 유저 정보를 조회합니다.")
    @GetMapping("/find/{userId}")
    public ResponseEntity<?> UserFind(@PathVariable String userId, HttpServletRequest request) {

        return ResponseEntity.status(HttpStatus.OK)
                .body(ResponseDto.response(HttpStatus.OK, "유저 정보 조회 성공", FindUserResponseDto.builder()
                        .userId("test123")
                        .stdNo("20230101")
                        .nickName("guest")
                        .univName("하이유니대학교")
                        .firstMajor("시각디자인학과")
                        .secondMajor("컴퓨터공학과")
                        .mbti("INTJ")
                        .userInterests(List.of(20L, 4L, 5L))
                        .imageUrl("https://example.com/image.jpg")
                        .build())
                );
//        UserFindResponseDto userFindResponseDto = FindUserUseCase.userFind(userId, request);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(ResponseDto.response(HttpStatus.OK, "유저 정보 조회 성공", userFindResponseDto));
    }

}
