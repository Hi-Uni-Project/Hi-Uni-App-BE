package com.project.hiuni.domain.post.dto.request;

import com.project.hiuni.domain.post.entity.Type;

public record PostUpdateNoReviewRequest(
        String title,
        String content,
        Type type
) {
}
