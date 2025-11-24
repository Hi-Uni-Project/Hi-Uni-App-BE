package com.project.hiuni.domain.record.resume.link.dto;

import com.project.hiuni.domain.record.resume.entity.Resume;
import com.project.hiuni.domain.record.resume.link.entity.Link;
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

  public Link toEntity(Resume resume) {
    return Link.of(
        this.linkName,
        this.linkUrl,
        resume
    );
  }
}
