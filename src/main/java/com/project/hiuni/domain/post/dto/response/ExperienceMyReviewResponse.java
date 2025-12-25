package com.project.hiuni.domain.post.dto.response;

import com.project.hiuni.domain.post.entity.Category;
import com.project.hiuni.domain.post.entity.Type;
import java.time.LocalDateTime;

public record ExperienceMyReviewResponse(
        Category category,
        Type type,
        String title,
        String content,
        String organizationName,
        String position,
        String positionRank,
        String whatWork,
        LocalDateTime startDate,
        LocalDateTime endDate
) implements PostMyReviewResponse {}