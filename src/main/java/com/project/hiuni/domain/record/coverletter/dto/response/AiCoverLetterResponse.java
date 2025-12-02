package com.project.hiuni.domain.record.coverletter.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AiCoverLetterResponse {

  private String answer;
  private Integer coverletterCnt;

}
