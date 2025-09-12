package com.project.hiuni.domain.post.dto.response;

import com.project.hiuni.domain.post.entity.Category;
import com.project.hiuni.domain.post.entity.Post;
import com.project.hiuni.domain.post.entity.Type;
import com.project.hiuni.domain.user.entity.User;
import java.time.LocalDateTime;

public record PostPreviewResponse(
        String nickname,
        String univName,
        String majorName,
        String userImageUrl,
        Long id,
        String title,
        Type type,
        Category category,
        int likeCount,
        int commentCount,
        LocalDateTime createdAt

) {
    public static PostPreviewResponse from (Post post) {

        User user = post.getUser();

        return new PostPreviewResponse(
                user.getNickname(),
                user.getUnivName(),
                user.getMajorName(),
                user.getImageUrl(),
                post.getId(),
                post.getTitle(),
                post.getType(),
                post.getCategory(),
                post.getLikeCount(),
                post.getCommentCount(),
                post.getCreatedAt()
        );
    }
}
