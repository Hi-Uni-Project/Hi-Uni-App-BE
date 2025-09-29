package com.project.hiuni.domain.comment.repository;

import com.project.hiuni.domain.comment.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentLikeRepository extends JpaRepository <CommentLike, Long> {

}
