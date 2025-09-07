package com.project.hiuni.domain.post.dto.request;

import com.project.hiuni.domain.post.entity.PostType;
import java.time.LocalDateTime;

public record PostCreateRequest(
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
}
