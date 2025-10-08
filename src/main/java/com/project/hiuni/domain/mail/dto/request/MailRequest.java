package com.project.hiuni.domain.mail.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class MailRequest {

  @NotNull(message = "이메일은 null 일 수 없습니다.")
  private String email;

  @NotNull(message = "학교명은 null 일 수 없습니다.")
  private String univName;

}
