package com.project.hiuni.domain.auth.dto.response;

import com.project.hiuni.domain.auth.entity.SocialProvider;
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
  private User user;

  @Getter
  @Setter
  @Builder
  public static class User {
    private Tos tos;
    private Univ univ;
    private Social social;
  }

  @Getter
  @Setter
  @Builder
  public static class Tos {
    private Boolean serviceTosIsAgreed;
    private Boolean personalInfoTosIsAgreed;
    private Boolean marketingTosIsAgreed;
    private Boolean serviceImprovementTosIsAgreed;
    private Boolean inPersonTosIsAgreed;
  }

  @Getter
  @Setter
  @Builder
  public static class Univ {
    private String univName;
    private String firstMajorName;
    private String secondMajorName;
    private String univEmail;
  }

  @Getter
  @Setter
  @Builder
  public static class Social {
    private String socialEmail;
    private SocialProvider provider;
  }



}