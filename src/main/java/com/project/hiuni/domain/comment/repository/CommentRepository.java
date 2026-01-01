package com.project.hiuni.domain.comment.repository;

import com.project.hiuni.domain.comment.entity.Comment;
import com.project.hiuni.domain.comment.projection.PostCommentCount;
import com.project.hiuni.domain.post.entity.Post;
import com.project.hiuni.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository <Comment, Long> {

    @Query("""
    select distinct p
    from Comment c
    join c.post p
    where c.user.id = :userId
    order by p.createdAt desc
""")
    List<Post> findPostsCommentedByUser(@Param("userId") Long userId);

    @Query("""
    SELECT c
    FROM Comment c
    JOIN FETCH c.user
    WHERE c.post.id = :postId
      AND c.parent IS NULL
    ORDER BY c.createdAt ASC
""")
    List<Comment> findParentCommentsByPostId(@Param("postId") Long postId);


    // 유저 댓글 전체삭제 (회원 탈퇴 시 사용)
    @Modifying
    @Query("""
    DELETE
    FROM Comment c
    WHERE c.user = :user
""")
    void deleteAllByUser(@Param("user") User user);

    // 유저가 작성한 게시글에 달린 대댓글 전체 삭제 (회원 탈퇴 시 사용)
    @Modifying
    @Query("""
     DELETE
     FROM Comment c
     WHERE c.post.user = :user
     """)
    void deleteAllByPostUser(@Param("user") User user);

    @Query("""
    SELECT COUNT(c)
    FROM Comment c
    WHERE c.post.id = :postId
""")
    int countAllByPostId(@Param("postId") Long postId);

    @Query("""
    select c.post.id as postId, count(c) as count
    from Comment c
    where c.post.id in :postIds
    group by c.post.id
    """)
    List<PostCommentCount> countByPostIds(@Param("postIds") List<Long> postIds);

}
