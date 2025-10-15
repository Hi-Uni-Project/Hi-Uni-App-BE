package com.project.hiuni.domain.comment.repository;

import com.project.hiuni.domain.comment.dto.response.CommentResponse;
import com.project.hiuni.domain.comment.entity.Comment;
import com.project.hiuni.domain.post.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CommentRepository extends JpaRepository <Comment, Long> {

    @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.post.id = :postId ORDER BY c.createdAt ASC")
    List<Comment> findAllByPostIdOrderByCreatedAtAsc(@Param("postId") Long postId);

    @Query("""
    select distinct p
    from Comment c
    join c.post p
    where c.user.id = :userId
    order by p.createdAt desc
""")
    List<Post> findPostsCommentedByUser(@Param("userId") Long userId);
}
