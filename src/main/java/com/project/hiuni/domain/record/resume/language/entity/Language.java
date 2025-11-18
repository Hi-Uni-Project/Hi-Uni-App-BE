package com.project.hiuni.domain.record.resume.language.entity;

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
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "resume_id")
  private Resume resume;
}
