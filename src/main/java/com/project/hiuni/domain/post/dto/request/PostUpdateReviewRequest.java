package com.project.hiuni.domain.post.dto.request;

import com.project.hiuni.domain.post.entity.Type;
import java.time.LocalDateTime;

public record PostUpdateReviewRequest(
        String title,
        String content,
        String companyName,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Type type,
        String method,
        String userPosition,
        String userWork,
        String whatLearn,
        String feelings,
        String imageUrl
) {
}
