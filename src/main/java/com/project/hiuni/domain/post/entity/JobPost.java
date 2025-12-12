package com.project.hiuni.domain.post.entity;

import com.project.hiuni.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post_job")
public class JobPost extends Post {

    private String companyName;
    private String appliedPosition;
    private String applyMethod;

    @Lob
    private String interviewQuestions;
    @Lob
    private String preparation;

    private String result;

    @Lob
    private String feelings;
    @Lob
    private String additional;

    @Builder
    public JobPost(String title, String content, Category category, String imageUrl, User user,
                   String companyName, String appliedPosition, String applyMethod,
                   String interviewQuestions, String preparation, String result,
                   String feelings, String additional) {
        super(title, content, category, Type.JOB, user);
        this.companyName = companyName;
        this.appliedPosition = appliedPosition;
        this.applyMethod = applyMethod;
        this.interviewQuestions = interviewQuestions;
        this.preparation = preparation;
        this.result = result;
        this.feelings = feelings;
        this.additional = additional;
    }

    @PrePersist @PreUpdate
    private void enforceType() { this.type = Type.JOB; }

    public void updateDetail(String companyName, String appliedPosition, String applyMethod,
                             String interviewQuestions, String preparation, String result,
                             String feelings, String additional) {
        this.companyName = companyName;
        this.appliedPosition = appliedPosition;
        this.applyMethod = applyMethod;
        this.interviewQuestions = interviewQuestions;
        this.preparation = preparation;
        this.result = result;
        this.feelings = feelings;
        this.additional = additional;
    }
}