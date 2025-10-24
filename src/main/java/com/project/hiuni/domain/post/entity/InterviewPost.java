package com.project.hiuni.domain.post.entity;

import com.project.hiuni.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post_interview")
public class InterviewPost extends Post {

    private String companyName;
    private String appliedPosition;
    private String interviewFormat; // 패널/일대다/코딩과제 등

    @Lob private String interviewQuestions;
    @Lob private String preparation;
    @Lob private String atmosphere;

    @Lob private String feelings;
    @Lob private String additional;

    @Builder
    public InterviewPost(String title, String content, Category category, String imageUrl, User user,
                         String companyName, String appliedPosition, String interviewFormat,
                         String interviewQuestions, String preparation, String atmosphere,
                         String feelings, String additional) {
        super(title, content, category, Type.INTERVIEW, imageUrl, user);
        this.companyName = companyName;
        this.appliedPosition = appliedPosition;
        this.interviewFormat = interviewFormat;
        this.interviewQuestions = interviewQuestions;
        this.preparation = preparation;
        this.atmosphere = atmosphere;
        this.feelings = feelings;
        this.additional = additional;
    }

    public void updateDetail(String companyName, String appliedPosition, String interviewFormat,
                             String interviewQuestions, String preparation, String atmosphere,
                             String feelings, String additional) {
        this.companyName = companyName;
        this.appliedPosition = appliedPosition;
        this.interviewFormat = interviewFormat;
        this.interviewQuestions = interviewQuestions;
        this.preparation = preparation;
        this.atmosphere = atmosphere;
        this.feelings = feelings;
        this.additional = additional;
    }
}