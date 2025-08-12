package com.project.hiuni.domain.auth.v1.controller;

import com.project.hiuni.domain.auth.docs.AuthApiDocumentation;
import com.project.hiuni.domain.auth.dto.request.AuthSocialRequest;
import com.project.hiuni.domain.auth.dto.response.AuthSocialResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthApiDocumentation {

  @PostMapping("/social")
  public AuthSocialResponse authSocial(@RequestBody AuthSocialRequest authSocialRequest) {

  }

}
