package com.project.hiuni.domain.comment.dto.response;

import com.project.hiuni.domain.comment.entity.Comment;

public record CommentUpdateResponse(
        Long id,
        String content,
        int likeCount,
        Long postId,
        Long userId
) {
    public static CommentUpdateResponse from(Comment comment) {
        return new CommentUpdateResponse(
                comment.getId(),
                comment.getContent(),
                comment.getLikeCount(),
                comment.getPost().getId(),
                comment.getUser().getId()
        );
    }
}
