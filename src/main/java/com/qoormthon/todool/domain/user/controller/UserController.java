package com.qoormthon.todool.domain.user.controller;

import com.qoormthon.todool.domain.user.dto.UserDto;
import com.qoormthon.todool.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/user")
@Tag(name = "유저 관련 api 입니다.", description = "로그인, 회원가입, 유저 정보 수정 등의 기능입니다.")
public class UserController {
    @Autowired
    private UserService userService;

    @Operation(summary = "회원가입 api", description = "이미지는 선택사항 입니다.")
    @PostMapping(
            value = "/signup",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public ResponseEntity<?> signUp(@RequestPart(value = "user") UserDto userDto
                                    ,@RequestPart(value = "image", required = false) MultipartFile file) {
        return userService.signUp(userDto, file);
    }
}
