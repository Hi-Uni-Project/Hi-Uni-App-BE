package com.project.hiuni.domain.post.dto.response;

import com.project.hiuni.domain.post.entity.Post;
import java.time.LocalDateTime;
import com.project.hiuni.domain.post.entity.*;

public record PostReviewResponse(
        Long id,
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
) {
    public static PostReviewResponse from(Post post) {

        if (post instanceof JobPost p) {
            return new PostReviewResponse(
                    p.getId(),
                    p.getTitle(),
                    p.getContent(),
                    p.getType(),
                    p.getImageUrl(),
                    p.getCompanyName(),
                    p.getAppliedPosition(),
                    p.getApplyMethod(),
                    p.getInterviewQuestions(),
                    p.getPreparation(),
                    p.getResult(),
                    p.getFeelings(),
                    p.getAdditional(),
                    null,
                    null
            );
        }

        if (post instanceof InternshipPost p) {
            return new PostReviewResponse(
                    p.getId(),
                    p.getTitle(),
                    p.getContent(),
                    p.getType(),
                    p.getImageUrl(),
                    p.getCompanyName(),
                    p.getDepartment(),
                    p.getTasks(),
                    p.getLearned(),
                    p.getFeelings(),
                    p.getAdditional(),
                    null,
                    null,
                    p.getStartDate(),
                    p.getEndDate()
            );
        }

        if (post instanceof InterviewPost p) {
            return new PostReviewResponse(
                    p.getId(),
                    p.getTitle(),
                    p.getContent(),
                    p.getType(),
                    p.getImageUrl(),
                    p.getCompanyName(),
                    p.getAppliedPosition(),
                    p.getInterviewFormat(),
                    p.getInterviewQuestions(),
                    p.getPreparation(),
                    p.getAtmosphere(),
                    p.getFeelings(),
                    p.getAdditional(),
                    null,
                    null
            );
        }

        if (post instanceof ExperiencePost p) {
            return new PostReviewResponse(
                    p.getId(),
                    p.getTitle(),
                    p.getContent(),
                    p.getType(),
                    p.getImageUrl(),
                    p.getOrganizationName(),
                    p.getPosition(),
                    p.getRank(),
                    p.getWhatWork(),
                    p.getRequiredSkills(),
                    p.getCharacteristics(),
                    p.getFeelings(),
                    p.getAdditional(),
                    p.getStartDate(),
                    p.getEndDate()
            );
        }

        if (post instanceof LicensePost p) {
            return new PostReviewResponse(
                    p.getId(),
                    p.getTitle(),
                    p.getContent(),
                    p.getType(),
                    p.getImageUrl(),
                    p.getCertificationName(),
                    p.getPrepDuration(),
                    p.getMaterials(),
                    p.getDifficulty(),
                    p.getStudyMethod(),
                    p.getTips(),
                    p.getFeelings(),
                    p.getAdditional(),
                    null,
                    null
            );
        }

        return new PostReviewResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getType(),
                post.getImageUrl(),
                null, null, null, null, null, null, null, null, null, null
        );
    }
}