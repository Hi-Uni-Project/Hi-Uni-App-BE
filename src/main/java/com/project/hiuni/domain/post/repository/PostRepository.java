package com.project.hiuni.domain.post.repository;

import com.project.hiuni.domain.post.dto.response.PostPreviewResponse;
import com.project.hiuni.domain.post.entity.Category;
import com.project.hiuni.domain.post.entity.Post;
import com.project.hiuni.domain.post.entity.Type;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository <Post, Long> {

    @Query("""
        select p
        from Post p
        join p.user u
        where p.createdAt >= :startDate
          and p.createdAt < :endDate
          and u.univName = :univName
        order by p.likeCount desc
    """)
    List<Post> findWeeklyHot(@Param("startDate") LocalDateTime startDate,
                             @Param("endDate") LocalDateTime endDate,
                             String univName);

    @Query("""
    select p
    from Post p
    join fetch p.user u
    where u.univName = :univName
    """)
    List<Post> findAllPosts(@Param("univName") String univName,
                            Sort sort);

    @Query("""
        select p
        from Post p
        join fetch p.user u
        where u.univName = :univName
          and (p.category = :category)
        """)
    List<Post> findAllPostsByCategory(
            @Param("univName") String univName,
            @Param("category") Category category,
            Sort sort);

    @Query("""
        select p
        from Post p
        join fetch p.user u
        where u.univName = :univName
          and (p.type = :type)
        """)
    List<Post> findAllPostsByType(
            @Param("univName") String univName,
            @Param("type") Type type,
            Sort sort);

    @Query("""
        select p
        from Post p
        join p.user u
        where u.univName = :univName
          and (
                :keyword is null or :keyword = '' or
                lower(p.title)   like concat('%', lower(:keyword), '%') or
                lower(p.content) like concat('%', lower(:keyword), '%')
              )
              and ( :category is null or p.category = :category )
        """)
    List<Post> searchByKeywordAndUnivAndCategory(@Param("keyword") String keyword,
                                      @Param("univName") String univName,
                                      @Param("category") Category category,
                                      Sort sort);
    @Query("""
    select p
    from Post p
    join p.user u
    where u.id = :userId
    order by p.createdAt desc
""")
    List<Post> findAllByUserId(@Param("userId") Long userId);
}
