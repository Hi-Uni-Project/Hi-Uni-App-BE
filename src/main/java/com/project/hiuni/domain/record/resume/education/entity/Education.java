package com.project.hiuni.domain.record.resume.education.entity;

import com.project.hiuni.domain.record.resume.entity.Resume;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "education")
public class Education {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String universityName;

  private LocalDateTime startDate; //재학 기간(입학일)
  private LocalDateTime endDate; //재학 기간(졸업일)

  @Enumerated(EnumType.STRING)
  private GraduationStatus graduationStatus;

  private String major;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "resume_id")
  private Resume resume;

  private Education(String universityName, LocalDateTime startDate, LocalDateTime endDate,
      GraduationStatus graduationStatus, String major, Resume resume) {
    this.universityName = universityName;
    this.startDate = startDate;
    this.endDate = endDate;
    this.graduationStatus = graduationStatus;
    this.major = major;
    this.resume = resume;
  }

  public static Education of(
      String universityName,
      LocalDateTime startDate,
      LocalDateTime endDate,
      GraduationStatus graduationStatus,
      String major,
      Resume resume
  ) {
    return new Education(
        universityName,
        startDate,
        endDate,
        graduationStatus,
        major,
        resume
    );
  }

  public void update(Education education) {
    this.universityName = education.universityName;
    this.startDate = education.startDate;
    this.endDate = education.endDate;
    this.graduationStatus = education.graduationStatus;
    this.major = education.major;
  }



}
