package com.project.hiuni.domain.comment.repository;

import com.project.hiuni.domain.comment.entity.CommentLike;
import com.project.hiuni.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {

    // 특정 유저가 특정 댓글에 좋아요 했는지 여부
    @Query("""
           SELECT (COUNT(cl) > 0)
           FROM CommentLike cl
           WHERE cl.comment.id = :commentId AND cl.user.id = :userId
           """)
    boolean existsByCommentIdAndUserId(@Param("commentId") Long commentId,
                                       @Param("userId") Long userId);

    // 특정 유저가 특정 댓글에 누른 좋아요 엔티티 조회 (취소 시 사용)
    @Query("""
           SELECT cl
           FROM CommentLike cl
           WHERE cl.comment.id = :commentId AND cl.user.id = :userId
           """)
    Optional<CommentLike> findByCommentIdAndUserId(@Param("commentId") Long commentId,
                                                   @Param("userId") Long userId);

    // 정합성 검사용 좋아요 전체 숫자 계산
    @Query("""
           SELECT COUNT(cl)
           FROM CommentLike cl
           WHERE cl.comment.id = :commentId
           """)
    long countCommentLikeByCommentId(@Param("commentId") Long commentId);


    // 유저가 누른 좋아요 전체 삭제 (회원 탈퇴 시 사용)
    @Modifying
    @Query("""
           DELETE
           FROM CommentLike cl
           WHERE cl.user = :user
           """)
    void deleteAllByUser(@Param("user") User user);

}