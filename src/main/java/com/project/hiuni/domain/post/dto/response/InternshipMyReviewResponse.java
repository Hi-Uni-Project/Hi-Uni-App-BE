package com.project.hiuni.domain.post.dto.response;

import com.project.hiuni.domain.post.entity.Category;
import com.project.hiuni.domain.post.entity.Type;
import java.time.LocalDateTime;

public record InternshipMyReviewResponse(
        Category category,
        Type type,
        String title,
        String content,
        String companyName,
        String department,
        String tasks,
        LocalDateTime startDate,
        LocalDateTime endDate
) implements PostMyReviewResponse {}