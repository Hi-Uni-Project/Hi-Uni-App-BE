package com.project.hiuni.domain.post.entity;

import com.project.hiuni.admin.common.BaseEntity;
import com.project.hiuni.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    private String companyName;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String userPosition;

    private String whatLearn;

    private String feelings;

    private String imageUrl;

    private int likeCount=0;

    private int bookmarkCount=0;

    private int viewCount=0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Builder
    public Post(String title, String content, String companyName,
                LocalDateTime startDate, LocalDateTime endDate,
                Type type, String userPosition,
                String whatLearn,
                String feelings,
                String imageUrl,
                User user) {
        this.title = title;
        this.content = content;
        this.companyName = companyName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.userPosition = userPosition;
        this.whatLearn = whatLearn;
        this.feelings = feelings;
        this.imageUrl = imageUrl;
        this.user = user;
    }

    public void incrementLikeCount() {
        likeCount++;
    }

    public void decrementLikeCount() {
        likeCount--;
    }

    public void incrementBookmarkCount() {
        bookmarkCount++;
    }

    public void decrementBookmarkCount() {
        bookmarkCount--;
    }

    public void incrementViewCount() {
        viewCount++;
    }

    public void updatePost(String title, String content, String companyName,
                           LocalDateTime startDate, LocalDateTime endDate,
                           Type type, String userPosition,
                           String whatLearn, String feelings, String imageUrl) {
        this.title = title;
        this.content = content;
        this.companyName = companyName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.type = type;
        this.userPosition = userPosition;
        this.whatLearn = whatLearn;
        this.feelings = feelings;
        this.imageUrl = imageUrl;
    }
}
