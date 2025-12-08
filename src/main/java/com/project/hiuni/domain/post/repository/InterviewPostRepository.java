package com.project.hiuni.domain.post.repository;

import com.project.hiuni.domain.post.entity.InterviewPost;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InterviewPostRepository extends JpaRepository<InterviewPost, Long> {

    @Query("""
    select i
    from InterviewPost i
    where i.user.id = :userId
      and i.companyName is not null
    """)
    List<InterviewPost> findReviewPostsByUser(@Param("userId") Long userId);
}
