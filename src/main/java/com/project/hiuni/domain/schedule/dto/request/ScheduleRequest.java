package com.project.hiuni.domain.schedule.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * 일정 생성 및 수정을 위한 요청 DTO
 */

@Getter
@Setter
@Builder
public class ScheduleRequest {

  @NotNull
  private LocalDateTime startDate;

  @NotNull
  private LocalDateTime endDate;

  @NotNull
  private String detail; //일정 명

  private String memo;


  @Min(1)
  @Max(6)
  private Long categoryId;

}
