package com.project.hiuni.domain.mail.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class MailRequest {

  @NotNull
  private String email;

}
