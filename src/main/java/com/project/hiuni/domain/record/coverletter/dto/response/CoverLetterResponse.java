package com.project.hiuni.domain.record.coverletter.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CoverLetterResponse {
  private List<CoverLetters> coverLetters;
  private Integer coverletterCnt;


  @Getter
  @Setter
  @Builder
  public static class CoverLetters {
    private Long coverLetterId;
    private String question;
    private String answer;
  }

}
