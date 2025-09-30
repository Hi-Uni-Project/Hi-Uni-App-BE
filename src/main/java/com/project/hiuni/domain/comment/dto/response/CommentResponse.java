package com.project.hiuni.domain.comment.dto.response;

import com.project.hiuni.domain.comment.entity.Comment;
import com.project.hiuni.domain.user.entity.User;

public record CommentResponse(
        Long id,
        String nickname,
        String firstMajorName,
        String secondMajorName,
        String usrImageUrl,
        String content,
        int likeCount
) {
    public static CommentResponse from(Comment comment) {

        User user = comment.getUser();

        return new CommentResponse(
                comment.getId(),
                user.getNickname(),
                user.getFirstMajorName(),
                user.getSecondMajorName(),
                user.getImageUrl(),
                comment.getContent(),
                comment.getLikeCount()
        );
    }
}
