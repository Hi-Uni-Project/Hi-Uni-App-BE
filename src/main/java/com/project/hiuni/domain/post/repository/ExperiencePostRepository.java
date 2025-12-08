package com.project.hiuni.domain.post.repository;

import com.project.hiuni.domain.post.entity.ExperiencePost;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ExperiencePostRepository extends JpaRepository<ExperiencePost, Long> {

    @Query("""
    select e 
    from ExperiencePost e
    where e.user.id = :userId
      and e.organizationName is not null
    """)
    List<ExperiencePost> findReviewPostsByUser(@Param("userId") Long userId);
}
