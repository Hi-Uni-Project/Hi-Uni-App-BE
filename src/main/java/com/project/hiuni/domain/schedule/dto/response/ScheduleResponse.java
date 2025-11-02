package com.project.hiuni.domain.schedule.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ScheduleResponse {

  private Long scheduleId;
  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private Category category;
  private String detail;
  private String time;
  private String memo;

  @Getter
  @Setter
  @Builder
  public static class Category {
    private Long categoryId;
    private String categoryName;
    private String backgroundColor;
    private String textColor;
  }



}
