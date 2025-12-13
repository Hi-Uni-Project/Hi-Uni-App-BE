package com.project.hiuni.domain.comment.dto.response;

import com.project.hiuni.domain.comment.entity.Comment;
import java.time.LocalDateTime;

public record CommentCreateResponse(
        Long id,
        String content,
        int likeCount,
        Long postId,
        Long userId,
        LocalDateTime createdAt
) {
    public static CommentCreateResponse from(Comment comment) {
        return new CommentCreateResponse(
                comment.getId(),
                comment.getContent(),
                comment.getLikeCount(),
                comment.getPost().getId(),
                comment.getUser().getId(),
                comment.getCreatedAt()
        );
    }
}
