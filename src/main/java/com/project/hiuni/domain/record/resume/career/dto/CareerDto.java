package com.project.hiuni.domain.record.resume.career.dto;

import com.project.hiuni.domain.record.resume.career.entity.Career;
import com.project.hiuni.domain.record.resume.entity.Resume;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CareerDto {

  private Long careerId;

  private String companyName;

  private LocalDateTime startDate; //재직 기간(시작일)

  private LocalDateTime endDate; //재직 기간(종료일)

  private String role; //직무

  private String position; //직급/직책

  private String jobDescription; //담당 업무 및 성과

  public Career toEntity(Resume resume) {

    return Career.of(
        this.companyName,
        this.startDate,
        this.endDate,
        this.role,
        this.position,
        this.jobDescription,
        resume
    );

  }
}
