package com.project.hiuni.domain.post.dto.response;

import com.project.hiuni.domain.post.entity.Category;
import com.project.hiuni.domain.post.entity.Post;
import com.project.hiuni.domain.post.entity.Type;
import com.project.hiuni.domain.user.entity.User;
import java.time.LocalDateTime;

public record PostCreateReviewResponse (
        String nickname,
        String univName,
        String majorName,
        String userImageUrl,
        Long id,
        String title,
        String content,
        String companyName,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Type type,
        Category category,
        String userPosition,
        String userWork,
        String whatLearn,
        String feelings,
        String imageUrl,
        int likeCount,
        int commentCount,
        int bookmarkCount,
        int viewCount,
        LocalDateTime createdAt
){
    public static PostCreateReviewResponse from(Post post) {

        User user = post.getUser();

        return new PostCreateReviewResponse(
                user.getNickname(),
                user.getUnivName(),
                user.getMajorName(),
                user.getImageUrl(),
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCompanyName(),
                post.getStartDate(),
                post.getEndDate(),
                post.getType(),
                post.getCategory(),
                post.getUserPosition(),
                post.getUserWork(),
                post.getWhatLearn(),
                post.getFeelings(),
                post.getImageUrl(),
                post.getLikeCount(),
                post.getCommentCount(),
                post.getBookmarkCount(),
                post.getViewCount(),
                post.getCreatedAt()
        );
    }
}