package com.project.hiuni.domain.post.entity;

import com.project.hiuni.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post_internship")
public class InternshipPost extends Post {

    private String companyName;
    private String department;
    @Lob private String tasks;
    @Lob private String learned;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Lob private String feelings;
    @Lob private String additional;

    @Builder
    public InternshipPost(String title, String content, Category category, String imageUrl, User user,
                          String companyName, String department, String tasks, String learned,
                          LocalDateTime startDate, LocalDateTime endDate,
                          String feelings, String additional) {
        super(title, content, category, Type.INTERNSHIP, imageUrl, user);
        this.companyName = companyName;
        this.department = department;
        this.tasks = tasks;
        this.learned = learned;
        this.startDate = startDate;
        this.endDate = endDate;
        this.feelings = feelings;
        this.additional = additional;
    }

    public void updateDetail(String companyName, String department, String tasks, String learned,
                             LocalDateTime startDate, LocalDateTime endDate,
                             String feelings, String additional) {
        this.companyName = companyName;
        this.department = department;
        this.tasks = tasks;
        this.learned = learned;
        this.startDate = startDate;
        this.endDate = endDate;
        this.feelings = feelings;
        this.additional = additional;
    }
}