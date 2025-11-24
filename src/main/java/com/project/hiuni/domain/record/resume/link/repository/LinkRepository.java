package com.project.hiuni.domain.record.resume.link.repository;

import com.project.hiuni.domain.record.resume.entity.Resume;
import com.project.hiuni.domain.record.resume.link.entity.Link;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkRepository extends JpaRepository<Link, Long> {

  List<Link> findAllByResume(Resume resume);
}
