package com.project.hiuni.domain.record.resume.skill.repository;

import com.project.hiuni.domain.record.resume.education.entity.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SkillRepository extends JpaRepository<Education, Long> {

}
