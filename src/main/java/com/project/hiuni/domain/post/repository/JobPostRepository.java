package com.project.hiuni.domain.post.repository;

import com.project.hiuni.domain.post.entity.JobPost;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface JobPostRepository extends JpaRepository<JobPost, Long> {

    @Query("""
    select j
    from JobPost j
    where j.user.id = :userId
      and j.companyName is not null
    """)
    List<JobPost> findReviewPostsByUser(@Param("userId") Long userId);
}
