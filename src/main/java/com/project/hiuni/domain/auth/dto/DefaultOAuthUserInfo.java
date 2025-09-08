package com.project.hiuni.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.SQLDeleteAll;

@Getter
@Builder
@AllArgsConstructor
public class DefaultOAuthUserInfo implements OAuthUserInfo{
  private final String id;
  private final String name;
  private final String email;

  public static DefaultOAuthUserInfo of(String id, String name, String email) {
    return DefaultOAuthUserInfo.builder()
        .id(id)
        .name(name)
        .email(email)
        .build();
  }
}
