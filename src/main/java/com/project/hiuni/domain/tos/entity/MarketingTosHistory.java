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
@Table(name = "marketing_tos_history")
public class MarketingTosHistory extends BaseEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "marketing_tos_id")
  private MarketingTos marketingTos;

  @Builder
  private MarketingTosHistory(User user, MarketingTos marketingTos) {
    this.user = user;
    this.marketingTos = marketingTos;
  }

  public static MarketingTosHistory of(User user, MarketingTos marketingTos) {
    return MarketingTosHistory.builder()
        .user(user)
        .marketingTos(marketingTos)
        .build();
  }

  public static MarketingTosHistory createTemp(User user) {
    return MarketingTosHistory.builder()
        .user(user)
        .marketingTos(null)
        .build();
  }
}
