package com.project.hiuni.domain.record.resume.repository;

import com.project.hiuni.domain.record.resume.entity.Resume;
import com.project.hiuni.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {


  Optional<Resume> findByUser(User user);
}
