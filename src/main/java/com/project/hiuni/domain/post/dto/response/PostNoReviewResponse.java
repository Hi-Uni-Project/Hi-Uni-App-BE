package com.project.hiuni.domain.post.dto.response;

import com.project.hiuni.domain.post.entity.Category;
import com.project.hiuni.domain.post.entity.Post;
import com.project.hiuni.domain.post.entity.Type;
import com.project.hiuni.domain.user.entity.User;
import java.time.LocalDateTime;

public record PostNoReviewResponse(
        String nickname,
        String univName,
        String majorName,
        String userImageUrl,
        Long id,
        String title,
        String content,
        Type type,
        Category category,
        String imageUrl,
        int likeCount,
        int commentCount,
        int bookmarkCount,
        int viewCount,
        LocalDateTime createdAt
) {
    public static PostNoReviewResponse from(Post post) {

        User user = post.getUser();

        return new PostNoReviewResponse(
                user.getNickname(),
                user.getUnivName(),
                user.getMajorName(),
                user.getImageUrl(),
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getType(),
                post.getCategory(),
                post.getImageUrl(),
                post.getLikeCount(),
                post.getCommentCount(),
                post.getBookmarkCount(),
                post.getViewCount(),
                post.getCreatedAt()
        );
    }
}