package com.project.hiuni.domain.auth.v1.controller;

import com.project.hiuni.domain.auth.docs.AuthApiDocumentation;
import com.project.hiuni.domain.auth.dto.request.AuthSocialRequest;
import com.project.hiuni.domain.auth.dto.response.AuthSocialResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/v1/auth")
public class AuthController implements AuthApiDocumentation {

  @PostMapping("/social")
  public AuthSocialResponse authSocial(
      @RequestBody @Valid AuthSocialRequest authSocialRequest,
      BindingResult bindingResult) {

    if(bindingResult.hasErrors()) {
      log.error("AuthSocialRequest validation failed :: {}", bindingResult.getAllErrors());

    }


  }

}
