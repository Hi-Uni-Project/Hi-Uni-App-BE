package com.project.hiuni.domain.post.repository;

import com.project.hiuni.domain.post.entity.Category;
import com.project.hiuni.domain.post.entity.Post;
import com.project.hiuni.domain.post.entity.Type;
import com.project.hiuni.domain.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository <Post, Long> {

    @Query("""
        select p
        from Post p
        join fetch p.user u
        where p.createdAt >= :startDate
          and p.createdAt < :endDate
          and u.univName = :univName
    """)
    List<Post> findWeeklyHot(@Param("startDate") LocalDateTime startDate,
                             @Param("endDate") LocalDateTime endDate,
                             @Param("univName") String univName);

    @Query("""
        select p
        from Post p
        join fetch p.user u
        where p.createdAt >= :startDate
          and p.createdAt < :endDate
          and u.univName = :univName
          and p.type = :type
    """)
    List<Post> findWeeklyHotByType(@Param("startDate") LocalDateTime startDate,
                                   @Param("endDate") LocalDateTime endDate,
                                   @Param("univName") String univName,
                                   @Param("type") Type type);

    @Query("""
        select p
        from Post p
        join fetch p.user u
        where p.createdAt >= :startDate
          and p.createdAt < :endDate
          and u.univName = :univName
          and p.category = :category
    """)
    List<Post> findWeeklyHotByCategory(@Param("startDate") LocalDateTime startDate,
                                       @Param("endDate") LocalDateTime endDate,
                                       @Param("univName") String univName,
                                       @Param("category") Category category);


    @Query("""
        select p
        from Post p
        join fetch p.user u
        where u.univName = :univName    """)
    List<Post> findAllPosts(@Param("univName") String univName);

    @Query("""
        select p
        from Post p
        join fetch p.user u
        where u.univName = :univName
          and (p.category = :category)
        """)
    List<Post> findAllPostsByCategory(
            @Param("univName") String univName,
            @Param("category") Category category);

    @Query("""
        select p
        from Post p
        join fetch p.user u
        where u.univName = :univName
          and (p.type = :type)
        """)
    List<Post> findAllPostsByType(
            @Param("univName") String univName,
            @Param("type") Type type);

    @Query("""
    select p
    from Post p
    join fetch p.user u
    where function('replace', trim(u.univName), ' ', '') =
          function('replace', trim(:univName), ' ', '')
      and (
            :keyword is null or :keyword = '' or
            p.title like concat('%', :keyword, '%')
          )
      and ( :category is null or p.category = :category )
    """)
    List<Post> searchByKeywordAndUnivAndCategory(@Param("keyword") String keyword,
                                                 @Param("univName") String univName,
                                                 @Param("category") Category category);

    @Query("""
    select p
    from Post p
    join fetch p.user u
    where function('replace', trim(u.univName), ' ', '') =
          function('replace', trim(:univName), ' ', '')
      and (
           :keyword is null or :keyword = '' or
           p.title like concat('%', :keyword, '%')
      )
    """)
    List<Post> searchByKeywordAndUniv(@Param("keyword") String keyword,
                                      @Param("univName") String univName);
    @Query("""
    select p
    from Post p
    join fetch p.user u
    where u.id = :userId
    order by p.createdAt desc
""")
    List<Post> findAllByUserId(@Param("userId") Long userId);

    // 유저가 작성한 게시글 전체 삭제 (회원 탈퇴 시 사용)
    @Modifying
    @Query("""
    delete
    from Post p
    where p.user = :user
""")
    void deleteAllByUserId(@Param("user") User user);

}
