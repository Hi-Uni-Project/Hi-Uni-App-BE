package com.project.hiuni.domain.record.resume.achievement.dto;

import com.project.hiuni.domain.record.resume.achievement.entity.Achievement;
import com.project.hiuni.domain.record.resume.achievement.entity.Type;
import com.project.hiuni.domain.record.resume.entity.Resume;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AchievementDto {
  private Long achievementId;

  private String activityName;

  private LocalDateTime periodDate;

  private Type type;

  private String achievementDescription;

  public Achievement toEntity(Resume resume) {

    return Achievement.of(
        this.activityName,
        this.periodDate,
        this.type,
        this.achievementDescription,
        resume
    );

  }
}
