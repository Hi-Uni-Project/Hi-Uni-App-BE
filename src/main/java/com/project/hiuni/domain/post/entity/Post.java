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
    private PostType postType;

    private String userPosition;

    private String whatLearn;

    private String feelings;

    private int likeCount;

    private int bookmarkCount;

    private int viewCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Builder
    public Post(String title, String content, String companyName,
                LocalDateTime startDate, LocalDateTime endDate,
                PostType postType, String userPosition,
                String whatLearn, String feelings, User user) {
        this.title = title;
        this.content = content;
        this.companyName = companyName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.postType = postType;
        this.userPosition = userPosition;
        this.whatLearn = whatLearn;
        this.feelings = feelings;
        this.user = user;
    }
}
