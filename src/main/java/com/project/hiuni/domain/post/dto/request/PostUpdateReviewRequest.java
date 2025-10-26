package com.project.hiuni.domain.post.dto.request;

import com.project.hiuni.domain.post.entity.Type;
import java.time.LocalDateTime;

public record PostUpdateReviewRequest(
        String title,
        String content,
        Type type,
        String imageUrl,
        String firstQuestion,
        String secondQuestion,
        String thirdQuestion,
        String fourthQuestion,
        String fifthQuestion,
        String sixthQuestion,
        String seventhQuestion,
        String eighthQuestion,
        LocalDateTime startDate,
        LocalDateTime endDate
) {}
