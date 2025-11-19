package com.project.hiuni.domain.record.resume.achievement.dto.response;

import com.project.hiuni.domain.record.resume.achievement.entity.Type;
import com.project.hiuni.domain.record.resume.language.entity.Level;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Lob;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AchievementResponse {
  private String activityName;

  private LocalDateTime periodDate;

  private Type type;

  private String achievementDescription;
}
