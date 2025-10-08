package com.project.hiuni.domain.mail.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CodeRequest {

  @NotEmpty(message = "인증 코드는 null 일 수 없습니다.")
  private String authCode;

  @NotEmpty(message = "인증 메일 ID는 null 일 수 없습니다.")
  private String authMailId;

}
