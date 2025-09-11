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
@Table(name = "service_improvement_tos_history")
public class ServiceImprovementTosHistory extends BaseEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "service_improvement_id")
  private ServiceImprovementTos serviceImprovementTos;

  @Builder
  public ServiceImprovementTosHistory(User user, ServiceImprovementTos serviceImprovementTos) {
    this.user = user;
    this.serviceImprovementTos = serviceImprovementTos;
  }

  public static ServiceImprovementTosHistory of(User user, ServiceImprovementTos serviceImprovementTos) {
    return ServiceImprovementTosHistory.builder()
        .user(user)
        .serviceImprovementTos(serviceImprovementTos)
        .build();
  }

  public static ServiceImprovementTosHistory createTemp(User user) {
    return ServiceImprovementTosHistory.builder()
        .user(user)
        .serviceImprovementTos(null)
        .build();
  }
}
