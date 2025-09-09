package com.project.hiuni.domain.schedule.entity;

import com.project.hiuni.admin.common.BaseEntity;
import com.project.hiuni.domain.user.entity.User;
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
@Table(name = "schedule")
@Entity
public class Schedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Type type;

    private String title;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    private Color color;

    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Builder
    public Schedule(Type type,
                    String title,
                    LocalDateTime startDate,
                    LocalDateTime endDate,
                    Color color,
                    String memo,
                    User user) {
        this.type = type;
        this.title = title;
        this.startDate = startDate;
        this.endDate = endDate;
        this.color = color;
        this.memo = memo;
        this.user = user;
    }
}
