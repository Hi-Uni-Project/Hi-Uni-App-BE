package com.project.hiuni.domain.tos.entity;

import com.project.hiuni.admin.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "personal_info_tos")
public class PersonalInfoTos extends BaseEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String tosContent;

  private String tosVersion;

  @Builder
  private PersonalInfoTos(String tosContent, String tosVersion) {
    this.tosContent = tosContent;
    this.tosVersion = tosVersion;
  }

  public static PersonalInfoTos of(String tosContent, String tosVersion) {
    return PersonalInfoTos.builder()
        .tosContent(tosContent)
        .tosVersion(tosVersion)
        .build();
  }
}
