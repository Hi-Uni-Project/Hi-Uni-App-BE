package com.project.hiuni.domain.tos.entity;

import com.project.hiuni.admin.common.BaseEntity;
import com.project.hiuni.domain.user.entity.User;
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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "personal_info_tos_history")
public class PersonalInfoTosHistory extends BaseEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "personal_info_tos_id")
  private PersonalInfoTos personalInfoTos;

  @Builder
  private PersonalInfoTosHistory(User user, PersonalInfoTos personalInfoTos) {
    this.user = user;
    this.personalInfoTos = personalInfoTos;
  }

  public static PersonalInfoTosHistory of(User user, PersonalInfoTos personalInfoTos) {
    return PersonalInfoTosHistory.builder()
        .user(user)
        .personalInfoTos(personalInfoTos)
        .build();
  }

  public static PersonalInfoTosHistory createTemp(User user) {
    return PersonalInfoTosHistory.builder()
        .user(user)
        .personalInfoTos(null)
        .build();
  }
}
