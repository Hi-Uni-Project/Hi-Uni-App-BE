package com.project.hiuni.domain.post.entity;

import com.project.hiuni.admin.common.BaseEntity;
import com.project.hiuni.domain.bookmark.entity.Bookmark;
import com.project.hiuni.domain.comment.entity.Comment;
import com.project.hiuni.domain.user.entity.User;
import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
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

    protected boolean isReview;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    protected User user;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostLike> postLikes = new ArrayList<>();

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

}