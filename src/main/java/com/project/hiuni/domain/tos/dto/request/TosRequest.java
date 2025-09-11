package com.project.hiuni.domain.tos.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class TosRequest {

  @NotNull
  boolean serviceTosIsAgreed;

  @NotNull
  boolean personalInfoTosIsAgreed;

  @NotNull
  boolean marketingTosIsAgreed;

  @NotNull
  boolean serviceImprovementTosIsAgreed;

  @NotNull
  boolean inPersonTosIsAgreed;

}
