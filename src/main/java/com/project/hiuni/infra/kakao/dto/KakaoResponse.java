package com.project.hiuni.infra.kakao.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class KakaoResponse {

  private Long id;  // 고유 아이디값

  @JsonProperty("kakao_account")
  private KakaoAccount kakaoAccount;

  @Getter
  public static class KakaoAccount {
    private String email;  // 이메일

    @JsonProperty("has_email")
    private Boolean hasEmail;

    @JsonProperty("email_needs_agreement")
    private Boolean emailNeedsAgreement;

    @JsonProperty("is_email_valid")
    private Boolean isEmailValid;

    @JsonProperty("is_email_verified")
    private Boolean isEmailVerified;

    private Profile profile;  // null일 수 있음

    @Getter
    public static class Profile {
      private String nickname;  // 닉네임
    }
  }

}
