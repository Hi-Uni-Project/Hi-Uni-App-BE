package com.project.hiuni.domain.schedule.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "scheduleDetail")
@Entity
public class ScheduleDetail {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "schedule_id")
  private Schedule schedule;

  private Long categoryId;

  @Column(length = 100)
  private String title;

  private String color;

  @Lob
  private String memo;

  @Builder
  public ScheduleDetail(Schedule schedule, Long categoryId, String title, String color, String memo) {
    this.schedule = schedule;
    this.categoryId = categoryId;
    this.title = title;
    this.color = color;
    this.memo = memo;
  }

  public static ScheduleDetail of(Schedule schedule, Long categoryId, String title, String color, String memo) {
    return ScheduleDetail.builder()
      .schedule(schedule)
      .categoryId(categoryId)
      .title(title)
      .color(color)
      .memo(memo)
      .build();
  }





}
