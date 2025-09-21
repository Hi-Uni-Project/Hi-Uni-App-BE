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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.List;
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

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    @OneToMany(mappedBy = "schedule")
    private List<ScheduleDetail> scheduleDetails;

    @Builder
    public Schedule(LocalDateTime startDate,
                    LocalDateTime endDate,
                    User user,
                    List<ScheduleDetail> scheduleDetails) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.scheduleDetails = scheduleDetails;
    }

    public static Schedule of(LocalDateTime startDate, LocalDateTime endDate, User user, List<ScheduleDetail> scheduleDetails) {
        return Schedule.builder()
            .startDate(startDate)
            .endDate(endDate)
            .user(user)
            .scheduleDetails(scheduleDetails)
            .build();
    }
}
