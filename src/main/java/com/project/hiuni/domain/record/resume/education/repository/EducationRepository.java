package com.project.hiuni.domain.record.resume.education.repository;

import com.project.hiuni.domain.record.resume.education.entity.Education;
import com.project.hiuni.domain.record.resume.entity.Resume;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationRepository extends JpaRepository<Education, Long> {

  List<Education> findAllByResume(Resume resume);

}
