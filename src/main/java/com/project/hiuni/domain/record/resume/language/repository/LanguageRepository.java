package com.project.hiuni.domain.record.resume.language.repository;

import com.project.hiuni.domain.record.resume.education.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LanguageRepository extends JpaRepository<Education, Long> {

}
