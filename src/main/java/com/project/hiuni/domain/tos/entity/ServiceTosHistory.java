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
@Table(name = "service_tos_history")
public class ServiceTosHistory extends BaseEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "service_tos_id")
  private ServiceTos serviceTos;

  @Builder
  public ServiceTosHistory(User user, ServiceTos serviceTos) {
    this.user = user;
    this.serviceTos = serviceTos;
  }

  public static ServiceTosHistory of(User user, ServiceTos serviceTos) {
    return ServiceTosHistory.builder()
        .user(user)
        .serviceTos(serviceTos)
        .build();
  }

  public static ServiceTosHistory createTemp(User user) {
    return ServiceTosHistory.builder()
        .user(user)
        .serviceTos(null)
        .build();
  }

}
