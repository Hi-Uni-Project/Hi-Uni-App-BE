package com.project.hiuni.domain.user.v1.controller;

import com.project.hiuni.domain.user.v1.service.UserV1Service;
import com.project.hiuni.global.common.dto.response.ResponseDTO;
import com.project.hiuni.global.security.core.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserV1Controller {

  private final UserV1Service userV1Service;

  @DeleteMapping("/me")
  public ResponseDTO<?> deleteUser(@AuthenticationPrincipal CustomUserDetails userDetails) {

    userV1Service.deleteUser(userDetails.getId());

    return ResponseDTO.of("회원 탈퇴에 성공하였습니다.");
  }



}
