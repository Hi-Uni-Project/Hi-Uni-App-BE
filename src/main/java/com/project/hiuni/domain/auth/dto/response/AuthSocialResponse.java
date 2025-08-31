package com.project.hiuni.domain.auth.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthSocialResponse {

  private String accessToken;
  private String refreshToken;
  private Boolean isSignUp;

}