package com.project.hiuni.domain.post.dto.response;

import com.project.hiuni.domain.post.entity.Post;
import com.project.hiuni.domain.user.entity.User;
import java.time.LocalDateTime;

public record WeeklyHotPost(
        String nickname,
        String univName,
        String majorName,
        Long id,
        String title,
        int likeCount,
        int commentCount,
        LocalDateTime createdAt

) {
    public static WeeklyHotPost from (Post post) {
        User user = post.getUser();

        return new WeeklyHotPost(
                user.getNickname(),
                user.getUnivName(),
                user.getMajorName(),
                post.getId(),
                post.getTitle(),
                post.getLikeCount(),
                post.getCommentCount(),
                post.getCreatedAt()
        );
    }
}
