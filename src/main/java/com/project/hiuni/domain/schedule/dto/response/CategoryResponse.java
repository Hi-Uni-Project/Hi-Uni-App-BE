package com.project.hiuni.domain.schedule.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CategoryResponse {

  private Long categoryId;
  private String categoryName;
  private String backgroundColor;
  private String textColor;

}
