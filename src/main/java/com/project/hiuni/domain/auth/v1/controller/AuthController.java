package com.project.hiuni.domain.auth.v1.controller;

import static com.project.hiuni.global.exception.ErrorCode.VALIDATION_FAILED;

import com.project.hiuni.domain.auth.docs.AuthApiDocumentation;
import com.project.hiuni.domain.auth.dto.request.AuthSocialRequest;
import com.project.hiuni.domain.auth.dto.response.AuthSocialResponse;
import com.project.hiuni.domain.auth.v1.service.AuthService;
import com.project.hiuni.global.exception.ValidationException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthApiDocumentation {

  private final AuthService authService;

  @PostMapping("/social")
  public AuthSocialResponse authSocial(
      @RequestBody @Valid AuthSocialRequest authSocialRequest, BindingResult bindingResult) {

    if(bindingResult.hasErrors()) {
      log.error("AuthSocialRequest validation failed :: {}", bindingResult.getAllErrors());
      throw new ValidationException(VALIDATION_FAILED);
    }

    AuthSocialResponse authSocialResponse = authService.socialLogin(authSocialRequest);

  }

}
