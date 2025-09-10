package com.project.hiuni.domain.mail.dto.response;

import groovy.transform.Sealed;
import lombok.Builder;
import lombok.Getter;

@Getter
@Sealed
@Builder
public class MailResponse {

  private String message;

}
