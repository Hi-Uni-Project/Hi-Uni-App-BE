package com.project.hiuni.domain.auth.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum SocialProvider {
  GOOGLE("google"),
  KAKAO("kakao"),
  NAVER("naver"),
  APPLE("apple");

  private final String value;

  SocialProvider(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public static SocialProvider fromValue(String value) {
    for (SocialProvider provider : SocialProvider.values()) {
      if (provider.getValue().equalsIgnoreCase(value)) {
        return provider;
      }
    }
    throw new IllegalArgumentException("지원하지 않는 소셜 provider 입니다.: " + value);
  }

  @JsonCreator
  public static SocialProvider fromString(String value) {
    return fromValue(value);
  }

  @JsonValue
  public String toString() {
    return value;
  }
}
