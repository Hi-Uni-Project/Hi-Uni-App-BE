package com.project.hiuni.domain.schedule.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScheduleResponse {

  private LocalDateTime date;
  private List<Schedule> schedule;


  @Getter
  @Setter
  public static class Schedule {
    private String category;
    private String detail;
    private String time;
  }

}
