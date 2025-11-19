package com.project.hiuni.domain.record.resume.project.repository;

import com.project.hiuni.domain.record.resume.education.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Education, Long> {

}
