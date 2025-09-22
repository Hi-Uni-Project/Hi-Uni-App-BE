package com.project.hiuni.domain.schedule.dto.request;

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

  private LocalDateTime startDate;
  private LocalDateTime endDate;
  private String detail; //일정 명
  private String memo;
  private Long categoryId;

}
