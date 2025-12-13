package com.project.hiuni.domain.record.resume.career.repository;

import com.project.hiuni.domain.record.resume.career.entity.Career;
import com.project.hiuni.domain.record.resume.entity.Resume;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CareerRepository extends JpaRepository<Career, Long> {

  List<Career> findAllByResume(Resume resume);

  @Modifying
  @Query("delete from Career c where c.resume = :resume")
  void deleteAllByResume(Resume resume);

}
