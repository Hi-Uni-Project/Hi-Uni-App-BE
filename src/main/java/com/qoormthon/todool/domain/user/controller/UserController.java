package com.qoormthon.todool.domain.user.controller;

import com.qoormthon.todool.domain.user.dto.UserDto;
import com.qoormthon.todool.domain.user.dto.UserLoginDto;
import com.qoormthon.todool.domain.user.service.CheckUserService;
import com.qoormthon.todool.domain.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
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

    @Autowired
    private CheckUserService checkUserService;

    @Operation(summary = "회원가입 api", description = "이미지는 선택사항 입니다.")
    @PostMapping(
            value = "/signup",
            consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}
    )
    public ResponseEntity<?> signUp(@RequestPart(value = "user") UserDto userDto
                                    ,@RequestPart(value = "image", required = false) MultipartFile file) {
        return userService.signUp(userDto, file);
    }

    @Operation(summary = "로그인 api", description = "유저 로그인 api 입니다. jwt 토큰을 발급합니다.")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginDto userLoginDto){
        return userService.login(userLoginDto);
    }

    @Operation(summary = "유저정보 조회 api", description = "유저 아이디로 유저 정보를 조회합니다.")
    @GetMapping("/find/{userId}")
    public ResponseEntity<?> searchUserInfo(@PathVariable String userId, HttpServletRequest request) {
        return userService.searchUserInfo(userId, request);
    }

    @Operation(summary = "유저 id 유효성 체크 api", description = "현재 유저 아이디가 사용 가능한지 체크합니다.")
    @GetMapping("/check/id/{userId}")
    public ResponseEntity<?> checkUserId(@PathVariable String userId) {
        return checkUserService.checkUserId(userId);
    }

    @Operation(summary = "유저 pwd 유효성 체크 api", description = "현재 유저 패스워드가 사용 가능한지 체크합니다.")
    @GetMapping("/check/pwd/{userPwd}")
    public ResponseEntity<?> checkUserPwd(@PathVariable String userPwd) {
        return checkUserService.checkUserPwd(userPwd);
    }

}
