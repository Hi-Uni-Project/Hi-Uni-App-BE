package com.project.hiuni.domain.post.repository;

import com.project.hiuni.domain.post.entity.Post;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository <Post, Long> {

    @Query("""
        select p
        from Post p
        join p.user u
        where p.createdAt >= :start
          and p.createdAt < :end
          and u.univName = :univName
        order by p.likeCount desc
    """)
    List<Post> findWeeklyHot(@Param("startDate") LocalDateTime startDate,
                             @Param("endDate") LocalDateTime endDate,
                             String univName);


    @Query("""
        select p
        from Post p
        join p.user u
        where p.createdAt >= :start
          and p.createdAt < :end
          and u.univName = :univName
    """)
    List<Post> findWeekly(@Param("startDate") LocalDateTime startDate,
                             @Param("endDate") LocalDateTime endDate,
                             String univName);

    @Query("""
    select p
    from Post p
    join p.user u
    where u.univName = :univName
    order by p.likeCount desc
    """)
    List<Post> sortPostByLike(@Param("univName") String univName);

    @Query("""
    select p
    from Post p
    join p.user u
    where u.univName = :univName
    order by p.createdAt desc
    """)
    List<Post> sortPostByDate(@Param("univName") String univName);

    @Query("""
    select p
    from Post p
    join p.user u
    where u.univName = :univName
    order by p.commentCount desc
    """)
    List<Post> sortPostByComment(@Param("univName") String univName);
    
}
