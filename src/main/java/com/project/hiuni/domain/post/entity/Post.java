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
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "post")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Lob
    protected String title;

    @Column(columnDefinition = "TEXT")
    @Lob
    protected String content;

    @Enumerated(EnumType.STRING)
    protected Category category;

    @Enumerated(EnumType.STRING)
    protected Type type;

    protected String imageUrl;

    protected int likeCount = 0;
    protected int bookmarkCount = 0;
    protected int viewCount = 0;
    protected int commentCount = 0;

    protected boolean isReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    protected User user;

    protected Post(String title, String content, Category category, Type type,
                   User user, boolean isReview) {
        this.title = title;
        this.content = content;
        this.category = category;
        this.type = type;
        this.user = user;
        this.isReview = isReview;
    }

    public void updateCommon(String title, String content, Category category) {
        this.title = title;
        this.content = content;
        this.category = category;
    }

    public void incrementLikeCount() { likeCount++;}

    public void decrementLikeCount() { likeCount--;}

    public void incrementBookmarkCount() { bookmarkCount++;}

    public void decrementBookmarkCount() { bookmarkCount--;}

    public void incrementViewCount() { viewCount++; }

    public void decrementViewCount() { viewCount--;}

    public void incrementCommentCount() { commentCount++; }

    public void decrementCommentCount() { commentCount--;}

    public void decreaseCommentCount(int count) {
        this.commentCount = Math.max(0, this.commentCount - count);
    }
}