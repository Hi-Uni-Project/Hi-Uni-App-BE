package com.project.hiuni.domain.record.resume.language.dto;

import com.project.hiuni.domain.record.resume.entity.Resume;
import com.project.hiuni.domain.record.resume.language.entity.Language;
import com.project.hiuni.domain.record.resume.language.entity.Level;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LanguageDto {
  private Long languageId;

  private String language;

  private Level level;

  public Language toEntity(Resume resume) {

    return Language.of(
        this.language,
        this.level,
        resume
    );

  }
}
