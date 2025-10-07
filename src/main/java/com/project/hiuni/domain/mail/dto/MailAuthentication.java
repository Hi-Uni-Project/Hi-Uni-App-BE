package com.project.hiuni.domain.mail.dto;

import java.security.SecureRandom;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MailAuthentication {

  private String authMailId;
  private String authCode;


  public static MailAuthentication from(String authMailId) {
    return MailAuthentication
        .builder()
        .authMailId(authMailId)
        .authCode(createAuthCode())
        .build();
  }

  public static String createAuthCode() {
    SecureRandom random = new SecureRandom();
    int code = 100000 + random.nextInt(900000);
    return String.valueOf(code);
  }

}
