package com.project.hiuni.domain.post.dto.response;

import com.project.hiuni.domain.post.entity.Category;
import com.project.hiuni.domain.post.entity.Post;
import com.project.hiuni.domain.post.entity.Type;
import com.project.hiuni.domain.user.entity.User;
import java.time.LocalDateTime;

public record PostPreviewResponse(
        String univName,
        String firstMajorName,
        String secondMajorName,
        Long id,
        String title,
        String content,
        Type type,
        Category category,
        int likeCount,
        int commentCount,
        int bookmarkCount,
        LocalDateTime createdAt,
        boolean isReview

) {
    public static PostPreviewResponse from (Post post, int commentCount) {

        User user = post.getUser();

        return new PostPreviewResponse(
                user.getUnivName(),
                user.getFirstMajorName(),
                user.getSecondMajorName(),
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getType(),
                post.getCategory(),
                post.getLikeCount(),
                commentCount,
                post.getBookmarkCount(),
                post.getCreatedAt(),
                post.isReview()
        );
    }

}
