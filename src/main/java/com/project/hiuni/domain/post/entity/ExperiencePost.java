package com.project.hiuni.domain.post.entity;

import com.project.hiuni.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post_experience")
public class ExperiencePost extends Post {

    private String organizationName;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    private String position;
    @Column(name = "position_rank")
    private String positionRank;

    @Lob
    private String whatWork;
    @Lob
    private String requiredSkills;
    @Lob
    private String characteristics;

    @Lob
    private String feelings;
    @Lob
    private String additional;

    @Builder
    public ExperiencePost(String title, String content, Category category, String imageUrl, User user,
                          String organizationName, LocalDateTime startDate, LocalDateTime endDate,
                          String position, String positionRank, String whatWork,
                          String requiredSkills, String characteristics,
                          String feelings, String additional) {
        super(title, content, category, Type.EXPERIENCE, user);
        this.organizationName = organizationName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
        this.positionRank = positionRank;
        this.whatWork = whatWork;
        this.requiredSkills = requiredSkills;
        this.characteristics = characteristics;
        this.feelings = feelings;
        this.additional = additional;
    }

    public void updateDetail(String organizationName, LocalDateTime startDate, LocalDateTime endDate,
                             String position, String positionRank, String whatWork,
                             String requiredSkills, String characteristics,
                             String feelings, String additional) {
        this.organizationName = organizationName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.position = position;
        this.positionRank = positionRank;
        this.whatWork = whatWork;
        this.requiredSkills = requiredSkills;
        this.characteristics = characteristics;
        this.feelings = feelings;
        this.additional = additional;
    }
}