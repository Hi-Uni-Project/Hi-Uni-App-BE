package com.project.hiuni.domain.record.resume.project.dto.response;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProjectResponse {

  private String projectName;

  private LocalDateTime startDate; //활동 기간(시작일)
  private LocalDateTime endDate; //활동 기간(종료일)

  private String role;

  private String experienceDescription; //경험 및 성과
}
