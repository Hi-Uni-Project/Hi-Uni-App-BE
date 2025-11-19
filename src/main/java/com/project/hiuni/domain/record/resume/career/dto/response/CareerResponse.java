package com.project.hiuni.domain.record.resume.career.dto.response;

import jakarta.persistence.Lob;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CareerResponse {

  private String companyName;

  private LocalDateTime startDate; //재직 기간(시작일)

  private LocalDateTime endDate; //재직 기간(종료일)

  private String role; //직무

  private String position; //직급/직책

  private String jobDescription; //담당 업무 및 성과
}
