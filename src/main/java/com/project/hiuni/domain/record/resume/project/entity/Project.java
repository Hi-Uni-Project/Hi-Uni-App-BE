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
import lombok.Getter;
import lombok.NoArgsConstructor;
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "resume_id")
  private Resume resume;
}
