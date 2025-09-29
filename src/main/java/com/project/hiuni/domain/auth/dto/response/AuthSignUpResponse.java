package com.project.hiuni.domain.auth.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthSignUpResponse {

  private String accessToken;
  private String refreshToken;

}
