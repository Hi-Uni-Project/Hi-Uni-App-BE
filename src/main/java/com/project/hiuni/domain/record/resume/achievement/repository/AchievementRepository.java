package com.project.hiuni.domain.record.resume.achievement.repository;

import com.project.hiuni.domain.record.resume.achievement.entity.Achievement;
import com.project.hiuni.domain.record.resume.entity.Resume;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface AchievementRepository extends JpaRepository<Achievement, Long> {
  List<Achievement> findAllByResume(Resume resume);

  @Modifying
  @Query("delete from Achievement a where a.resume = :resume")
  void deleteAllByResume(Resume resume);
}
