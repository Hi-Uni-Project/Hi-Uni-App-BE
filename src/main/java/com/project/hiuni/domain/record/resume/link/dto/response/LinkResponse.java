package com.project.hiuni.domain.record.resume.link.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LinkResponse {
  private Long linkId;

  private String linkName;

  private String linkUrl;
}
