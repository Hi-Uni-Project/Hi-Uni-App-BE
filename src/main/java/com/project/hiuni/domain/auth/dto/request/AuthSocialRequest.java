package com.project.hiuni.domain.auth.dto.request;

import com.project.hiuni.domain.auth.entity.SocialProvider;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthSocialRequest {
  private String idToken;
  private SocialProvider provider;
}
