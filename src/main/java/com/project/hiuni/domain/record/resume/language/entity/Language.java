package com.project.hiuni.domain.record.resume.language.entity;

import com.project.hiuni.domain.record.resume.entity.Resume;
import com.project.hiuni.domain.record.resume.language.dto.LanguageDto;
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
@Table(name = "language")
public class Language {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String language;

  @Enumerated(EnumType.STRING)
  private Level level;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "resume_id")
  private Resume resume;

  @Builder
  private Language(String language, Level level, Resume resume) {
    this.language = language;
    this.level = level;
    this.resume = resume;
  }

  public static Language of(String language, Level level, Resume resume) {
    return Language.builder()
        .language(language)
        .level(level)
        .resume(resume)
        .build();
  }

  public void update(Language language) {
    this.language = language.getLanguage();
    this.level = language.getLevel();
  }

  public LanguageDto toDto() {
    return LanguageDto.builder()
        .languageId(this.id)
        .language(this.language)
        .level(this.level)
        .build();
  }
}
