package com.project.hiuni.domain.auth.v1.controller;

import com.project.hiuni.domain.auth.dto.request.AuthSignUpRequest;
import com.project.hiuni.domain.auth.dto.request.AuthSocialRequest;
import com.project.hiuni.domain.auth.dto.response.AuthSignUpResponse;
import com.project.hiuni.domain.auth.dto.response.AuthSocialResponse;
import com.project.hiuni.domain.auth.dto.response.TokenRefreshResponse;
import com.project.hiuni.domain.auth.v1.service.AuthService;
import com.project.hiuni.global.common.dto.response.ResponseDTO;
import com.project.hiuni.global.exception.ErrorCode;
import com.project.hiuni.global.exception.ValidationException;
import com.project.hiuni.global.security.core.CustomUserDetails;
import com.project.hiuni.global.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthController는 소셜 로그인, 회원가입, 엑세스 토큰 발급을 처리하는 API를 담당합니다.
 * 클라이언트는 이 API를 통해 소셜 로그인을 시도하고, 성공 시 accessToken, refreshToken, isSignUp 여부를 받을 수 있습니다.
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

  private final AuthService authService;
  private final JwtTokenProvider jwtTokenProvider;

  /**
   * 소셜 로그인/회원가입 API
   * @param authSocialRequest 소셜 로그인 요청 정보
   * @return AuthSocialResponse 소셜 로그인 응답 정보(accessToken, refreshToken, isSignUp 여부 반환)
   */
  @PostMapping("/social")
  public ResponseDTO<AuthSocialResponse> authSocial(
      @RequestBody @Valid AuthSocialRequest authSocialRequest) {

    //소셜 로그인 완료 후 accessToken, refreshToken, isSignUp 여부를 반환합니다
    AuthSocialResponse response = authService.socialLogin(authSocialRequest);
    return ResponseDTO.of(response, "소셜 로그인/회원가입에 성공하였습니다.");
  }

  @PostMapping("/refresh")
  public ResponseDTO<TokenRefreshResponse> refreshToken(HttpServletRequest httpServletRequest) {
    String accessToken = authService.refreshToken(httpServletRequest);

    TokenRefreshResponse response = TokenRefreshResponse.
        builder()
        .accessToken(accessToken)
        .build();
    return ResponseDTO.of(response, "토큰 재발급에 성공하였습니다.");
  }

  @PostMapping("/signup")
  public ResponseDTO<AuthSignUpResponse> authSignUp(@RequestBody @Valid AuthSignUpRequest authSignUpRequest, BindingResult bindingResult) {

    if(bindingResult.hasErrors()) {
      log.error("AuthSocialRequest validation failed :: {}", bindingResult.getAllErrors());
      throw new ValidationException(ErrorCode.INVALID_INPUT_VALUE);
    }

    AuthSignUpResponse authSignUpResponse = authService.authSignUp(authSignUpRequest);

    return ResponseDTO.of(authSignUpResponse, "회원가입에 성공하였습니다.");

  }


}
