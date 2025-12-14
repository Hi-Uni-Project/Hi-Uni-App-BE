package com.project.hiuni.domain.comment.dto.response;

import com.project.hiuni.domain.comment.entity.Comment;
import com.project.hiuni.domain.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public record CommentResponse(
        Long id,
        String nickname,
        String firstMajorName,
        String secondMajorName,
        String content,
        int likeCount,
        boolean isLiked,
        LocalDateTime createdAt,
        List<CommentResponse> commentReplies
) {
    public static CommentResponse from(Comment comment, Set<Long> likedSet) {

        User user = comment.getUser();

        return new CommentResponse(
                comment.getId(),
                user.getNickname(),
                user.getFirstMajorName(),
                user.getSecondMajorName(),
                comment.getContent(),
                comment.getLikeCount(),
                likedSet.contains(comment.getId()),
                comment.getCreatedAt(),
                comment.getChildren().stream()
                        .map(child -> from(child, likedSet))
                        .toList()
        );
    }
}
