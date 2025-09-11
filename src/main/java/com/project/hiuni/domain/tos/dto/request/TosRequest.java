package com.project.hiuni.domain.tos.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TosRequest {

  @NotNull
  private Boolean serviceTosIsAgreed;

  @NotNull
  private Boolean personalInfoTosIsAgreed;

  @NotNull
  private Boolean marketingTosIsAgreed;

  @NotNull
  private Boolean serviceImprovementTosIsAgreed;

  @NotNull
  private Boolean inPersonTosIsAgreed;

}
