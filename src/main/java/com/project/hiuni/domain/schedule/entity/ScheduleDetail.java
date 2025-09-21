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

  @Enumerated(EnumType.STRING)
  private Category category;

  @Column(length = 100)
  private String title;

  private String color;

  @Lob
  private String memo;

  @Builder
  public ScheduleDetail(Schedule schedule, Category category, String title, String color, String memo) {
    this.schedule = schedule;
    this.category = category;
    this.title = title;
    this.color = color;
    this.memo = memo;
  }

  public static ScheduleDetail of(Schedule schedule, Category category, String title, String color, String memo) {
    return ScheduleDetail.builder()
      .schedule(schedule)
      .category(category)
      .title(title)
      .color(color)
      .memo(memo)
      .build();
  }





}
