package com.project.hiuni.domain.post.dto.response;

import com.project.hiuni.domain.post.entity.Post;
import java.time.LocalDateTime;

public record PostWeeklyHotPreviewResponse(
        Long id,
        String title,
        int likeCount,
        int commentCount,
        LocalDateTime createdAt,
        boolean isReview
) {
    public static PostWeeklyHotPreviewResponse from(Post post) {
        return new PostWeeklyHotPreviewResponse(
                post.getId(),
                post.getTitle(),
                post.getLikeCount(),
                post.getCommentCount(),
                post.getCreatedAt(),
                post.isReview()
        );
    }
}
