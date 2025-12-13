package com.project.hiuni.domain.comment.dto.response;

import com.project.hiuni.domain.comment.entity.Comment;
import com.project.hiuni.domain.user.entity.User;
import java.util.List;

public record CommentResponse(
        Long id,
        String nickname,
        String firstMajorName,
        String secondMajorName,
        String content,
        int likeCount,
        List<CommentResponse> commentReplies
) {
    public static CommentResponse from(Comment comment) {

        User user = comment.getUser();

        return new CommentResponse(
                comment.getId(),
                user.getNickname(),
                user.getFirstMajorName(),
                user.getSecondMajorName(),
                comment.getContent(),
                comment.getLikeCount(),
                comment.getChildren().stream()
                        .map(CommentResponse::from)
                        .toList()
        );
    }
}
