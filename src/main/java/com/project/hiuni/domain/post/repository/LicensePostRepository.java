package com.project.hiuni.domain.post.repository;

import com.project.hiuni.domain.post.entity.LicensePost;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LicensePostRepository extends JpaRepository<LicensePost, Long> {

    @Query("""
    select l
    from LicensePost l
    where l.user.id = :userId
      and l.certificationName is not null
    """)
    List<LicensePost> findReviewPostsByUser(@Param("userId") Long userId);
}
