package com.project.hiuni.domain.record.resume.link.repository;

import com.project.hiuni.domain.record.resume.education.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Education, Long> {

}
