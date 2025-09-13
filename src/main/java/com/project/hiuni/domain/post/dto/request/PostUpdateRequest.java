package com.project.hiuni.domain.post.dto.request;

import com.project.hiuni.domain.post.entity.Category;
import com.project.hiuni.domain.post.entity.Type;
import java.time.LocalDateTime;

public record PostUpdateRequest(
        String title,
        String content,
        String companyName,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Type type,
        String userPosition,
        String userWork,
        String whatLearn,
        String feelings,
        String imageUrl
) {
}
