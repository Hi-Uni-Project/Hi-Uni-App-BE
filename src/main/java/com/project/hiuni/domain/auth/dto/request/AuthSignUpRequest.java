package com.project.hiuni.domain.auth.dto.request;

import com.project.hiuni.domain.auth.entity.SocialProvider;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthSignUpRequest {

  @NotNull
  private Tos tos;

  @NotNull
  private Univ univ;

  @NotNull
  private AuthSocialRequest social;

  @Getter
  @Setter
  public static class Tos {
    @NotNull(message = "약관 동의 여부는 필수입니다.")
    private Boolean serviceTosIsAgreed;
    @NotNull(message = "개인정보 처리방침 동의 여부는 필수입니다.")
    private Boolean personalInfoTosIsAgreed;
    @NotNull(message = "마케팅 정보 수신 동의 여부는 필수입니다.")
    private Boolean marketingTosIsAgreed;
    @NotNull(message = "서비스 개선을 위한 정보 수신 동의 여부는 필수입니다.")
    private Boolean serviceImprovementTosIsAgreed;
    @NotNull(message = "본인 정보로 가입했는지 여부는")
    private Boolean inPersonTosIsAgreed;
  }

  @Getter
  @Setter
  public static class Univ {
    @NotNull(message = "대학교 이름은 필수입니다.")
    private String univName;

    private String firstMajorName;
    private String secondMajorName;

    @NotNull(message = "학교 이메일은 필수입니다.")
    private String univEmail;
  }

}
