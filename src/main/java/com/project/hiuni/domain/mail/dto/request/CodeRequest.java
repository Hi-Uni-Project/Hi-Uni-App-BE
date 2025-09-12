package com.project.hiuni.domain.mail.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CodeRequest {

  @NotNull
  private String authCode;

}
