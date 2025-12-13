package com.project.hiuni.domain.post.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.hiuni.domain.post.entity.*;
import com.project.hiuni.domain.user.entity.User;

import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PostReviewResponse(
        String nickname,
        String univName,
        String firstMajorName,
        String secondMajorName,

        // 공통(Post)
        Long id,
        String title,
        String content,
        Type type,
        Category category,
        boolean isReview,
        int likeCount,
        int commentCount,
        int bookmarkCount,
        int viewCount,
        LocalDateTime createdAt,

        String companyName,
        String appliedPosition,
        String applyMethod,
        String interviewQuestions,
        String preparation,
        String result,
        String feelings,
        String additional,

        // INTERNSHIP
        String department,
        String tasks,
        String learned,

        // INTERVIEW
        String interviewFormat,
        String atmosphere,

        // EXPERIENCE
        String organizationName,
        String position,
        String positionRank,
        String whatWork,
        String requiredSkills,
        String characteristics,

        // 기간 (INTERNSHIP / EXPERIENCE)
        LocalDateTime startDate,
        LocalDateTime endDate,

        // LICENSE
        String certificationName,
        String prepDuration,
        String materials,
        String difficulty,
        String studyMethod,
        String tips
) {

    public static PostReviewResponse from(Post post) {
        String nickname = null;
        String univName = null;
        String firstMajorName = null;
        String secondMajorName = null;

        User u = post.getUser();
        boolean isReview = post.isReview();
        if (u != null) {
            nickname = u.getNickname();
            univName = u.getUnivName();
            firstMajorName = u.getFirstMajorName();
            secondMajorName = u.getSecondMajorName();
        }

        Long id = post.getId();
        String title = post.getTitle();
        String content = post.getContent();
        Type type = post.getType();
        Category category = post.getCategory();
        int likeCount = post.getLikeCount();
        int commentCount = post.getCommentCount();
        int bookmarkCount = post.getBookmarkCount();
        int viewCount = post.getViewCount();
        LocalDateTime createdAt = post.getCreatedAt();

        String companyName = null;
        String appliedPosition = null;
        String applyMethod = null;
        String interviewQuestions = null;
        String preparation = null;
        String result = null;
        String feelings = null;
        String additional = null;

        String department = null;
        String tasks = null;
        String learned = null;

        String interviewFormat = null;
        String atmosphere = null;

        String organizationName = null;
        String position = null;
        String positionRank = null;
        String whatWork = null;
        String requiredSkills = null;
        String characteristics = null;

        LocalDateTime startDate = null;
        LocalDateTime endDate = null;

        String certificationName = null;
        String prepDuration = null;
        String materials = null;
        String difficulty = null;
        String studyMethod = null;
        String tips = null;

        if (post instanceof JobPost p) {
            companyName = p.getCompanyName();
            appliedPosition = p.getAppliedPosition();
            applyMethod = p.getApplyMethod();
            interviewQuestions = p.getInterviewQuestions();
            preparation = p.getPreparation();
            result = p.getResult();
            feelings = p.getFeelings();
            additional = p.getAdditional();
        } else if (post instanceof InternshipPost p) {
            companyName = p.getCompanyName();
            department = p.getDepartment();
            tasks = p.getTasks();
            learned = p.getLearned();
            startDate = p.getStartDate();
            endDate = p.getEndDate();
            feelings = p.getFeelings();
            additional = p.getAdditional();
        } else if (post instanceof InterviewPost p) {
            companyName = p.getCompanyName();
            appliedPosition = p.getAppliedPosition();
            interviewFormat = p.getInterviewFormat();
            interviewQuestions = p.getInterviewQuestions();
            preparation = p.getPreparation();
            atmosphere = p.getAtmosphere();
            feelings = p.getFeelings();
            additional = p.getAdditional();
        } else if (post instanceof ExperiencePost p) {
            organizationName = p.getOrganizationName();
            startDate = p.getStartDate();
            endDate = p.getEndDate();
            position = p.getPosition();
            positionRank = p.getPositionRank();
            whatWork = p.getWhatWork();
            requiredSkills = p.getRequiredSkills();
            characteristics = p.getCharacteristics();
            feelings = p.getFeelings();
            additional = p.getAdditional();
        } else if (post instanceof LicensePost p) {
            certificationName = p.getCertificationName();
            prepDuration = p.getPrepDuration();
            materials = p.getMaterials();
            difficulty = p.getDifficulty();
            studyMethod = p.getStudyMethod();
            tips = p.getTips();
            feelings = p.getFeelings();
            additional = p.getAdditional();
        }

        return new PostReviewResponse(
                nickname, univName, firstMajorName, secondMajorName,
                id, title, content, type, category, isReview,
                likeCount, commentCount, bookmarkCount, viewCount, createdAt,
                companyName, appliedPosition, applyMethod, interviewQuestions,
                preparation, result, feelings, additional,
                department, tasks, learned,
                interviewFormat, atmosphere,
                organizationName, position, positionRank, whatWork, requiredSkills, characteristics,
                startDate, endDate,
                certificationName, prepDuration, materials, difficulty, studyMethod, tips
        );
    }
}