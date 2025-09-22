package com.project.hiuni.domain.schedule.entity;

import com.project.hiuni.admin.common.BaseEntity;
import com.project.hiuni.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "schedule")
@Entity
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    private Long categoryId;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Column(length = 100)
    private String title; // = detail (일정 명)v

    @Lob
    private String memo;


    @Builder
    public Schedule(LocalDateTime startDate, LocalDateTime endDate, User user, Long categoryId, String title, String memo) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.categoryId = categoryId;
        this.title = title;
        this.memo = memo;
    }

    public static Schedule of(LocalDateTime startDate, LocalDateTime endDate, User user, Long categoryId, String title, String memo) {
        return Schedule.builder()
            .startDate(startDate)
            .endDate(endDate)
            .user(user)
            .categoryId(categoryId)
            .title(title)
            .memo(memo)
            .build();
    }
}
