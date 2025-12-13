package com.project.hiuni.domain.record.resume.language.repository;

import com.project.hiuni.domain.record.resume.entity.Resume;
import com.project.hiuni.domain.record.resume.language.entity.Language;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LanguageRepository extends JpaRepository<Language, Long> {

  List<Language> findAllByResume(Resume resume);

  @Modifying
  @Query("delete from Language l where l.resume = :resume")
  void deleteAllByResume(Resume resume);
}
