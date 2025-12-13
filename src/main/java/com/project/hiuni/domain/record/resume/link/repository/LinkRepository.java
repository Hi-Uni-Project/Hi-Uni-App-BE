package com.project.hiuni.domain.record.resume.link.repository;

import com.project.hiuni.domain.record.resume.entity.Resume;
import com.project.hiuni.domain.record.resume.link.entity.Link;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface LinkRepository extends JpaRepository<Link, Long> {

  List<Link> findAllByResume(Resume resume);

  @Modifying
  @Query("delete from Link l where l.resume = :resume")
  void deleteAllByResume(Resume resume);
}
