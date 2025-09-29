package com.project.hiuni.domain.post.repository;

import com.project.hiuni.domain.post.entity.PostLike;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {

    //특정 유저가 특정 게시글 좋아요 눌렀는지 확인
    @Query("SELECT (COUNT(pl) > 0) " +
            "FROM PostLike pl " +
            "WHERE pl.post.id = :postId AND pl.user.id = :userId")
    boolean existsByPostIdAndUserId(@Param("postId") Long postId,
                                    @Param("userId") Long userId);

    // 특정 유저가 특정 댓글에 누른 좋아요 엔티티 조회 (취소 시 사용)
    @Query("SELECT pl " +
            "FROM PostLike pl " +
            "WHERE pl.post.id = :postId AND pl.user.id = :userId")
    Optional<PostLike> findByPostIdAndUserId(@Param("postId") Long postId,
                                             @Param("userId") Long userId);

    //정합성 검사용 좋아요 전체 숫자 계산
    @Query("SELECT COUNT(pl) " +
            "FROM PostLike pl " +
            "WHERE pl.post.id = :postId")
    long countPostLikeByPostId(@Param("postId") Long postId);
}
