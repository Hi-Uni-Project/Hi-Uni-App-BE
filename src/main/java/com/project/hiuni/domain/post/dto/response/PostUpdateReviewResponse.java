package com.project.hiuni.domain.post.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.project.hiuni.domain.post.entity.*;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PostUpdateReviewResponse(
        Long id,
        String title,
        String content,
        Type type,

        // ===== Job =====
        String companyName,
        String appliedPosition,
        String applyMethod,
        String interviewQuestions,
        String preparation,
        String result,
        String feelings,
        String additional,

        // ===== Internship =====
        String department,
        String tasks,
        String learned,
        LocalDateTime startDate,
        LocalDateTime endDate,

        // ===== Interview =====
        String interviewFormat,
        String atmosphere,

        // ===== Experience =====
        String organizationName,
        String position,
        String positionRank,
        String whatWork,
        String requiredSkills,
        String characteristics,

        // ===== License =====
        String certificationName,
        String prepDuration,
        String materials,
        String difficulty,
        String studyMethod,
        String tips
) {
    public static PostUpdateReviewResponse from(Post post) {
        Long id         = post.getId();
        String title    = post.getTitle();
        String content  = post.getContent();
        Type type       = post.getType();

        String companyName = null, appliedPosition = null, applyMethod = null, interviewQuestions = null,
                preparation = null, result = null, feelings = null, additional = null;

        String department = null, tasks = null, learned = null;
        LocalDateTime startDate = null, endDate = null;

        String interviewFormat = null, atmosphere = null;

        String organizationName = null, position = null, positionRank = null,
                whatWork = null, requiredSkills = null, characteristics = null;

        String certificationName = null, prepDuration = null, materials = null, difficulty = null,
                studyMethod = null, tips = null;

        // 타입별 매핑
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
            position = p.getPosition();
            positionRank = p.getPositionRank();
            whatWork = p.getWhatWork();
            requiredSkills = p.getRequiredSkills();
            characteristics = p.getCharacteristics();
            feelings = p.getFeelings();
            additional = p.getAdditional();
            startDate = p.getStartDate();
            endDate = p.getEndDate();

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

        return new PostUpdateReviewResponse(
                id, title, content, type,
                // Job
                companyName, appliedPosition, applyMethod, interviewQuestions, preparation, result, feelings, additional,
                // Internship
                department, tasks, learned, startDate, endDate,
                // Interview
                interviewFormat, atmosphere,
                // Experience
                organizationName, position, positionRank, whatWork, requiredSkills, characteristics,
                // License
                certificationName, prepDuration, materials, difficulty, studyMethod, tips
        );
    }
}