package com.project.hiuni.domain.record.resume.project.dto;

import com.project.hiuni.domain.record.resume.entity.Resume;
import com.project.hiuni.domain.record.resume.project.entity.Project;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ProjectDto {

  private Long projectId;

  private String projectName;

  private LocalDateTime startDate; //활동 기간(시작일)
  private LocalDateTime endDate; //활동 기간(종료일)

  private String role;

  private String experienceDescription; //경험 및 성과

  public Project toEntity(Resume resume) {

    return Project.of(
        this.projectName,
        this.startDate,
        this.endDate,
        this.role,
        this.experienceDescription,
        resume
    );

  }
}
