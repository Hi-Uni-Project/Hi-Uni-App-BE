package com.project.hiuni.domain.comment.repository;

import com.project.hiuni.domain.comment.entity.Comment;
import com.project.hiuni.domain.post.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository <Comment, Long> {
    List<Comment> findByPost(Post post);

}
