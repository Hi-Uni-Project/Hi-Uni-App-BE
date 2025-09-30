package com.project.hiuni.domain.post.dto.response;

import com.project.hiuni.domain.post.entity.Category;
import com.project.hiuni.domain.post.entity.Post;
import com.project.hiuni.domain.post.entity.Type;
import com.project.hiuni.domain.user.entity.User;
import java.time.LocalDateTime;

public record PostPreviewResponse(
        String nickname,
        String univName,
        String firstMajorName,
        String secondMajorName,
        String userImageUrl,
        Long id,
        String title,
        String content,
        Type type,
        Category category,
        int likeCount,
        int commentCount,
        int bookmarkCount,
        LocalDateTime createdAt

) {
    public static PostPreviewResponse from (Post post) {

        User user = post.getUser();

        return new PostPreviewResponse(
                user.getNickname(),
                user.getUnivName(),
                user.getFirstMajorName(),
                user.getSecondMajorName(),
                user.getImageUrl(),
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getType(),
                post.getCategory(),
                post.getLikeCount(),
                post.getCommentCount(),
                post.getBookmarkCount(),
                post.getCreatedAt()
        );
    }
}
