package com.project.hiuni.domain.post.dto.response;

import com.project.hiuni.domain.post.entity.Post;
import java.time.LocalDateTime;

public record PostReviewResponse(
        String companyName,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String userPosition,
        String userWork,
        String whatLearn,
        String feelings
) {
    public static PostReviewResponse from(Post post) {
        return new PostReviewResponse(
                post.getCompanyName(),
                post.getStartDate(),
                post.getEndDate(),
                post.getUserPosition(),
                post.getUserWork(),
                post.getWhatLearn(),
                post.getFeelings()
        );
    }
}
