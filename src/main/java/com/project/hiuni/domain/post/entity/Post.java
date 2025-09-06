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

    private String company_name;

    private LocalDateTime start_date;

    private LocalDateTime end_date;

    @Enumerated(EnumType.STRING)
    private PostType post_type;

    private String user_position;

    private String what_learn;

    private String feelings;

    private int like_count;

    private int bookmark_count;

    private int view_count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Builder
    public Post(String title, String content, String company_name,
                LocalDateTime start_date, LocalDateTime end_date,
                PostType post_type, String user_position,
                String what_learn, String feelings, User user) {
        this.title = title;
        this.content = content;
        this.company_name = company_name;
        this.start_date = start_date;
        this.end_date = end_date;
        this.post_type = post_type;
        this.user_position = user_position;
        this.what_learn = what_learn;
        this.feelings = feelings;
        this.user = user;
    }
}
