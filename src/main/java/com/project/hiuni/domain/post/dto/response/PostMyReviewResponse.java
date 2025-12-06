package com.project.hiuni.domain.post.dto.response;

import com.project.hiuni.domain.post.entity.Category;
import com.project.hiuni.domain.post.entity.Type;

public record PostMyReviewResponse(
        Category category,
        Type type,
        String title,
        String content
) {
}
