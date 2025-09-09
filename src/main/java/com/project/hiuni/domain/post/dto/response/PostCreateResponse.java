package com.project.hiuni.domain.post.dto.response;

import com.project.hiuni.domain.post.entity.Post;
import com.project.hiuni.domain.post.entity.Type;
import java.time.LocalDateTime;

public record PostCreateResponse (
    Long id,
    String title,
    String content,
    String companyName,
    LocalDateTime startDate,
    LocalDateTime endDate,
    Type type,
    String userPosition,
    String whatLearn,
    String feelings,
    String imageUrl
){
    public static PostCreateResponse from(Post post) {
        return new PostCreateResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getCompanyName(),
                post.getStartDate(),
                post.getEndDate(),
                post.getType(),
                post.getUserPosition(),
                post.getWhatLearn(),
                post.getFeelings(),
                post.getImageUrl()
        );
    }
}
