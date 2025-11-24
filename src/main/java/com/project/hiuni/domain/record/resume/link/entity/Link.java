package com.project.hiuni.domain.record.resume.link.entity;

import com.project.hiuni.domain.record.resume.entity.Resume;
import com.project.hiuni.domain.record.resume.link.dto.LinkDto;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "link")
public class Link {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String linkName;

  private String linkUrl;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "resume_id")
  private Resume resume;

  @Builder
  private Link(String linkName, String linkUrl, Resume resume) {
    this.linkName = linkName;
    this.linkUrl = linkUrl;
    this.resume = resume;
  }

  public static Link of(String linkName, String linkUrl, Resume resume) {
    return Link.builder()
        .linkName(linkName)
        .linkUrl(linkUrl)
        .resume(resume)
        .build();
  }

  public void update(Link link) {
    this.linkName = link.getLinkName();
    this.linkUrl = link.getLinkUrl();
  }

  public LinkDto toDto() {
    return LinkDto.builder()
        .linkId(this.id)
        .linkName(this.linkName)
        .linkUrl(this.linkUrl)
        .build();
  }


}
