package com.project.hiuni.domain.post.dto.response;

import com.project.hiuni.domain.post.entity.Category;
import com.project.hiuni.domain.post.entity.Post;
import com.project.hiuni.domain.post.entity.Type;
import com.project.hiuni.domain.user.entity.User;
import java.time.LocalDateTime;

public record PostUpdateNoReviewResponse (
        String univName,
        Long id,
        String title,
        String content,
        Type type,
        Category category,
        int likeCount,
        int commentCount,
        int bookmarkCount,
        int viewCount,
        LocalDateTime createdAt
){
    public static PostUpdateNoReviewResponse from(Post post) {

        User user = post.getUser();

        return new PostUpdateNoReviewResponse(
                user.getUnivName(),
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getType(),
                post.getCategory(),
                post.getLikeCount(),
                post.getCommentCount(),
                post.getBookmarkCount(),
                post.getViewCount(),
                post.getCreatedAt()
        );
    }
}