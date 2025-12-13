package com.project.hiuni.domain.record.resume.skill.repository;

import com.project.hiuni.domain.record.resume.entity.Resume;
import com.project.hiuni.domain.record.resume.skill.entity.Skill;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SkillRepository extends JpaRepository<Skill, Long> {

  List<Skill> findAllByResume(Resume resume);

  @Modifying
  @Query("delete from Skill s where s.resume = :resume")
  void deleteAllByResume(Resume resume);
}
