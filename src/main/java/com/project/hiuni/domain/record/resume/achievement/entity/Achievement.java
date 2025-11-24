package com.project.hiuni.domain.record.resume.achievement.entity;

import com.project.hiuni.domain.record.resume.achievement.dto.AchievementDto;
import com.project.hiuni.domain.record.resume.entity.Resume;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "achievement")
public class Achievement {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String activityName;

  private LocalDateTime periodDate;

  @Enumerated(EnumType.STRING)
  private Type type;

  @Lob
  private String achievementDescription;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "resume_id")
  private Resume resume;

  @Builder
  private Achievement(String activityName, LocalDateTime periodDate, Type type, String achievementDescription, Resume resume) {
    this.activityName = activityName;
    this.periodDate = periodDate;
    this.type = type;
    this.achievementDescription = achievementDescription;
    this.resume = resume;
  }

  public static Achievement of(String activityName, LocalDateTime periodDate, Type type, String achievementDescription, Resume resume) {
    return Achievement.builder()
        .activityName(activityName)
        .periodDate(periodDate)
        .type(type)
        .achievementDescription(achievementDescription)
        .resume(resume)
        .build();
  }

  public void update(Achievement achievement) {
    this.activityName = achievement.getActivityName();
    this.periodDate = achievement.getPeriodDate();
    this.type = achievement.getType();
    this.achievementDescription = achievement.getAchievementDescription();
  }

  public AchievementDto toDto() {
    return AchievementDto.builder()
        .achievementId(this.id)
        .activityName(this.activityName)
        .periodDate(this.periodDate)
        .type(this.type)
        .achievementDescription(this.achievementDescription)
        .build();
  }



}
