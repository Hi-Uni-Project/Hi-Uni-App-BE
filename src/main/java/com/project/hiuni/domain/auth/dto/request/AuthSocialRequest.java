package com.project.hiuni.domain.auth.dto.request;

import com.project.hiuni.domain.auth.entity.SocialProvider;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AuthSocialRequest {

  @NotNull(message = "토큰은 필수입니다.")
  private String authToken;

  @NotNull(message = "소셜 provider는 필수입니다.")
  private SocialProvider provider;
}
