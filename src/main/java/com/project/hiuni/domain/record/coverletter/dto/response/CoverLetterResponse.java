package com.project.hiuni.domain.record.coverletter.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CoverLetterResponse {
  private Long coverLetterId;
  private String question;
  private String answer;
}
