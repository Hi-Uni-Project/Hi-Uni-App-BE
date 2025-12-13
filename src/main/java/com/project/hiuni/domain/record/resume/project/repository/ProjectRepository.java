package com.project.hiuni.domain.record.resume.project.repository;

import com.project.hiuni.domain.record.resume.entity.Resume;
import com.project.hiuni.domain.record.resume.project.entity.Project;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project, Long> {


  List<Project> findAllByResume(Resume resume);

  @Modifying
  @Query("delete from Project p where p.resume = :resume")
  void deleteAllByResume(Resume resume);
}
