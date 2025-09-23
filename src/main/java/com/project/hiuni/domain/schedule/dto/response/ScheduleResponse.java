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

  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private String category;
  private String detail;
  private String time;
  private String color;
  private String memo;



}
