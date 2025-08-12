package com.project.hiuni.domain.auth.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthSocialRequest {
  private String provider
}
