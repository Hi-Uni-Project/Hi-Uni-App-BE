package com.project.hiuni.domain.record.resume.career.repository;

import com.project.hiuni.domain.record.resume.career.entity.Career;
import com.project.hiuni.domain.record.resume.entity.Resume;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerRepository extends JpaRepository<Career, Long> {

  List<Career> findAllByResume(Resume resume);

}
