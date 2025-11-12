package com.project.hiuni.domain.record.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RecordOverviewResponse {
  private String title; //이력서 제목
  private List<CoverLetterResponse> coverLetters; //자기소개서 질문 + 답변
  private String image_url;

  @Getter
  @Setter
  @Builder
  public static class CoverLetterResponse {
    private String question;
    private String answer;
  }

}

