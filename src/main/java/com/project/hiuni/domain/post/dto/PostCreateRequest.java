package com.project.hiuni.domain.post.dto;

import java.time.LocalDateTime;

public record PostCreateRequest(
        String title,
        String content,
        String companyName,
        LocalDateTime startDate,
        LocalDateTime endDate,
        String postType,
        String userPosition,
        String whatLearn,
        String feelings,
        String imageUrl
) {
}
