package com.project.hiuni.domain.post.repository;

import com.project.hiuni.domain.post.entity.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {
}
