package com.project.hiuni.domain.post.entity;

import com.project.hiuni.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "post_license")
public class LicensePost extends Post {

    private String certificationName;
    private String prepDuration;

    @Lob
    private String materials;

    private String difficulty;

    @Lob
    private String studyMethod;
    @Lob
    private String tips;
    @Lob
    private String feelings;
    @Lob
    private String additional;

    @Builder
    public LicensePost(String title, String content, Category category, String imageUrl, User user,
                       String certificationName, String prepDuration, String materials,
                       String difficulty, String studyMethod, String tips,
                       String feelings, String additional) {
        super(title, content, category, Type.LICENSE, imageUrl, user);
        this.certificationName = certificationName;
        this.prepDuration = prepDuration;
        this.materials = materials;
        this.difficulty = difficulty;
        this.studyMethod = studyMethod;
        this.tips = tips;
        this.feelings = feelings;
        this.additional = additional;
    }

    public void updateDetail(String certificationName, String prepDuration, String materials,
                             String difficulty, String studyMethod, String tips,
                             String feelings, String additional) {
        this.certificationName = certificationName;
        this.prepDuration = prepDuration;
        this.materials = materials;
        this.difficulty = difficulty;
        this.studyMethod = studyMethod;
        this.tips = tips;
        this.feelings = feelings;
        this.additional = additional;
    }
}