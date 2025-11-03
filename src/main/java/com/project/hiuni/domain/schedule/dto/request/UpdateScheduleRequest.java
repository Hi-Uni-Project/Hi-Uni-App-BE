package com.project.hiuni.domain.schedule.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateScheduleRequest {

  @NotNull
  private LocalDateTime startDate;

  @NotNull
  private LocalDateTime endDate;

  @NotNull
  private String detail; //일정 명

  private String memo;


  @Min(1)
  @Max(9)
  private Long categoryId;
}
