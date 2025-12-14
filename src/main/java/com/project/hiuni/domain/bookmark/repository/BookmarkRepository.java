package com.project.hiuni.domain.bookmark.repository;

import com.project.hiuni.domain.bookmark.entity.Bookmark;
import com.project.hiuni.domain.post.entity.Post;
import com.project.hiuni.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {

    //특정 유저가 특정 게시글 북마크 했는지 확인
    @Query("""
           SELECT (COUNT(b) > 0)
           FROM Bookmark b
           WHERE b.post.id = :postId AND b.user.id = :userId
           """)
    boolean existsByPostIdAndUserId(@Param("postId") Long postId,
                                    @Param("userId") Long userId);

    //특정 유저가 특정 게시글 북마크 한 유저 조회
    @Query("""
           SELECT b
           FROM Bookmark b
           WHERE b.post.id = :postId AND b.user.id = :userId
           """)
    Optional<Bookmark> findByPostIdAndUserId(@Param("postId") Long postId,
                                         @Param("userId") Long userId);

    @Query("""
       SELECT b.post
       FROM Bookmark b
       WHERE b.user.id = :userId
       """)
    List<Post> findMyPostsBookmarked(@Param("userId") Long userId);


    // 유저가 누른 북마크 전체 삭제 (회원 탈퇴 시 사용)
    @Modifying
    @Query("""
           DELETE
           FROM Bookmark b
           WHERE b.user = :user
           """)
    void deleteAllByUser(@Param("user") User user);

    // 내 글에 달린 북마크 전체 삭제 (회원 탈퇴 시 사용)
    @Modifying
    @Query("""
            DELETE
            FROM Bookmark b
            WHERE b.post.user = :user
            """)
    void deleteAllByPostUser(@Param("user") User user);

}
