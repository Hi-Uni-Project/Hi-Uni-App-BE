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
    private ScheduleType scheduleType;

    private String title;

    private LocalDateTime start_date;

    private LocalDateTime end_date;

    @Enumerated(EnumType.STRING)
    private ColorType colorType;

    private String memo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="user_id", nullable = false)
    private User user;

    @Builder
    public Schedule(ScheduleType scheduleType,
                    String title,
                    LocalDateTime startdate,
                    LocalDateTime enddate,
                    ColorType colorType,
                    String memo,
                    User user) {
        this.scheduleType = scheduleType;
        this.title = title;
        this.start_date = startdate;
        this.end_date = enddate;
        this.colorType = colorType;
        this.memo = memo;
        this.user = user;
    }
}
