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
@Table(name = "in_person_tos_history")
public class InPersonTosHistory extends BaseEntity {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "in_person_id")
  private InPersonTos inPersonTos;

  @Builder
  private InPersonTosHistory(User user, InPersonTos inPersonTos) {
    this.user = user;
    this.inPersonTos = inPersonTos;
  }

  public static InPersonTosHistory of(User user, InPersonTos inPersonTos) {
    return InPersonTosHistory.builder()
        .user(user)
        .inPersonTos(inPersonTos)
        .build();
  }

  public static InPersonTosHistory createTemp(User user) {
    return InPersonTosHistory.builder()
        .user(user)
        .inPersonTos(null)
        .build();
  }
}
