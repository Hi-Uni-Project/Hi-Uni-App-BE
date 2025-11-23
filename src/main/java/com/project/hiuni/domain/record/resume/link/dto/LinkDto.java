package com.project.hiuni.domain.record.resume.link.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LinkDto {
  private Long linkId;

  private String linkName;

  private String linkUrl;
}
