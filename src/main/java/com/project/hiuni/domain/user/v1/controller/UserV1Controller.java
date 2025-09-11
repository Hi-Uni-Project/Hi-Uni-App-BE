package com.project.hiuni.domain.user.v1.controller;

import com.project.hiuni.global.security.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserV1Controller {

  private final JwtTokenProvider jwtTokenProvider;

  @GetMapping("/token-test")
  public String tokenTest() {

    String token = jwtTokenProvider.createAccessToken("social123", "social123");

    return token;

  }



}
