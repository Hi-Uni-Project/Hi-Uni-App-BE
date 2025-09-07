package com.project.hiuni.domain.post.dto.response;

import com.project.hiuni.domain.post.entity.Post;
import com.project.hiuni.domain.post.entity.PostType;
import java.time.LocalDateTime;

public record PostUpdateResponse(
        Long id,
        String title,
        String content,
        String companyName,
        LocalDateTime startDate,
        LocalDateTime endDate,
        PostType postType,
        String userPosition,
        String whatLearn,
        String feelings,
        String imageUrl
) {
    public static PostUpdateResponse from(Post post) {

        return new PostUpdateResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCompanyName(),
                post.getStartDate(),
                post.getEndDate(),
                post.getPostType(),
                post.getUserPosition(),
                post.getWhatLearn(),
                post.getFeelings(),
                post.getImageUrl()
        );
    }
}
