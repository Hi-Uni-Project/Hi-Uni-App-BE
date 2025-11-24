package com.project.hiuni.domain.record.resume.education.dto;

import com.project.hiuni.domain.record.resume.education.entity.Education;
import com.project.hiuni.domain.record.resume.education.entity.GraduationStatus;
import com.project.hiuni.domain.record.resume.entity.Resume;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EducationDto {

  private Long educationId;

  private String universityName;

  private LocalDateTime startDate; //재학 기간(입학일)
  private LocalDateTime endDate; //재학 기간(졸업일)

  private GraduationStatus graduationStatus;

  private String major;

  public Education toEntity(Resume resume) {
    return Education.of(
        this.universityName,
        this.startDate,
        this.endDate,
        this.graduationStatus,
        this.major,
        resume

    );
  }
}
