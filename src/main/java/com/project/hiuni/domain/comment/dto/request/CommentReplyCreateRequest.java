package com.project.hiuni.domain.comment.dto.request;

public record CommentReplyCreateRequest(
        String content,
        Long parentCommentId
) {
}
