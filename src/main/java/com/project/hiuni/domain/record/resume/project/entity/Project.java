package com.project.hiuni.domain.record.resume.project.entity;

import com.project.hiuni.domain.record.resume.entity.Resume;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "project")
public class Project {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String projectName;

  private LocalDateTime startDate; //활동 기간(시작일)
  private LocalDateTime endDate; //활동 기간(종료일)

  private String role;

  @Lob
  private String experienceDescription; //경험 및 성과

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "resume_id")
  private Resume resume;

  @Builder
  private Project(String projectName, LocalDateTime startDate, LocalDateTime endDate, String role, String experienceDescription, Resume resume) {
    this.projectName = projectName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.role = role;
    this.experienceDescription = experienceDescription;
    this.resume = resume;
  }

  public static Project of(
      String projectName,
      LocalDateTime startDate,
      LocalDateTime endDate,
      String role,
      String experienceDescription,
      Resume resume
  ) {
    return Project.builder()
        .projectName(projectName)
        .startDate(startDate)
        .endDate(endDate)
        .role(role)
        .experienceDescription(experienceDescription)
        .resume(resume)
        .build();
  }

  public void update(Project project) {
    this.projectName = project.getProjectName();
    this.startDate = project.getStartDate();
    this.endDate = project.getEndDate();
    this.role = project.getRole();
    this.experienceDescription = project.getExperienceDescription();
  }
}
