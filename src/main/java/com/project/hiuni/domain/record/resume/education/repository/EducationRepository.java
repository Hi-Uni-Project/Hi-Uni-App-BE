package com.project.hiuni.domain.record.resume.education.repository;

import com.project.hiuni.domain.record.resume.education.entity.Education;
import com.project.hiuni.domain.record.resume.entity.Resume;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EducationRepository extends JpaRepository<Education, Long> {

  List<Education> findAllByResume(Resume resume);

  @Modifying
  @Query("delete from Education e where e.resume = :resume")
  void deleteAllByResume(Resume resume);

}
