package com.project.hiuni.domain.record.coverletter.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AiCoverLetterRequest {

  private String role;
  private String question;

}
