package com.project.hiuni.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
@AllArgsConstructor
public class DefaultOAuthUserInfo implements OAuthUserInfo{
  private final String socialId;
  private final String name;
  private final String email;

  @Override
  public String getSocialId() {
    return socialId;
  }

  @Override
  public String getName() {
    return name;
  }

  @Override
  public String getEmail() {
    return email;
  }

  public static DefaultOAuthUserInfo of(String socialId, String name, String email) {
    return DefaultOAuthUserInfo.builder()
        .socialId(socialId)
        .name(name)
        .email(email)
        .build();
  }
}
