package com.project.hiuni.domain.post.repository;

import com.project.hiuni.domain.post.entity.InternshipPost;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InternshipPostRepository extends JpaRepository<InternshipPost, Long> {

    @Query("""
    select i 
    from InternshipPost i
    where i.user.id = :userId
      and i.companyName is not null
    """)
    List<InternshipPost> findReviewPostsByUser(@Param("userId") Long userId);
}
