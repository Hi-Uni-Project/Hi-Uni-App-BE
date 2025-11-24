package com.project.hiuni.domain.record.resume.career.entity;

import com.project.hiuni.domain.record.resume.career.dto.CareerDto;
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
@Table(name = "career")
public class Career {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String companyName;

  private LocalDateTime startDate; //재직 기간(시작일)

  private LocalDateTime endDate; //재직 기간(종료일)

  private String role; //직무

  private String position; //직급/직책

  @Lob
  private String jobDescription; //담당 업무 및 성과

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "resume_id")
  private Resume resume;

  @Builder
  private Career(Long id, String companyName, LocalDateTime startDate, LocalDateTime endDate, String role, String position, String jobDescription, Resume resume) {
    this.id = id;
    this.companyName = companyName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.role = role;
    this.position = position;
    this.jobDescription = jobDescription;
    this.resume = resume;
  }

  public static Career of(String companyName, LocalDateTime startDate, LocalDateTime endDate, String role, String position, String jobDescription, Resume resume) {
    return Career.builder()
        .companyName(companyName)
        .startDate(startDate)
        .endDate(endDate)
        .role(role)
        .position(position)
        .jobDescription(jobDescription)
        .resume(resume)
        .build();
  }

  public void update(Career career) {
    this.companyName = career.getCompanyName();
    this.startDate = career.getStartDate();
    this.endDate = career.getEndDate();
    this.role = career.getRole();
    this.position = career.getPosition();
    this.jobDescription = career.getJobDescription();
  }

  public CareerDto toDto() {
    return CareerDto.builder()
        .careerId(this.getId())
        .companyName(this.companyName)
        .startDate(this.startDate)
        .endDate(this.endDate)
        .role(this.role)
        .position(this.position)
        .jobDescription(this.jobDescription)
        .build();
  }
}
