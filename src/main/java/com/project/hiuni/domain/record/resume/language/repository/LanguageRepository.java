package com.project.hiuni.domain.record.resume.language.repository;

import com.project.hiuni.domain.record.resume.entity.Resume;
import com.project.hiuni.domain.record.resume.language.entity.Language;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Language, Long> {

  List<Language> findAllByResume(Resume resume);
}
