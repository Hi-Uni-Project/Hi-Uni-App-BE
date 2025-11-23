package com.project.hiuni.domain.record.resume.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AiAboutMeResponse {

  private int aboutMeCnt;
  private String aboutMe;

}
