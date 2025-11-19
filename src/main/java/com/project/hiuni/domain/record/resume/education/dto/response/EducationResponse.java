package com.project.hiuni.domain.record.resume.education.dto.response;

import com.project.hiuni.domain.record.resume.education.entity.GraduationStatus;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EducationResponse {

  private Long educationId;

  private String universityName;

  private LocalDateTime startDate; //재학 기간(입학일)
  private LocalDateTime endDate; //재학 기간(졸업일)

  private GraduationStatus graduationStatus;

  private String major;
}
