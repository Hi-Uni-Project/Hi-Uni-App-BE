package com.project.hiuni.domain.auth.entity;

import com.project.hiuni.admin.common.BaseEntity;
import com.project.hiuni.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Auth extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, length = 255)
  private String refreshToken;

  @Builder
  private Auth(Long id, String refreshToken) {
    this.id = id;
    this.refreshToken = refreshToken;
  }

  public static Auth from(String refreshToken) {
    return Auth.builder()
        .id(null)
        .refreshToken(refreshToken)
        .build();
  }

  public void updateRefreshToken(String refreshToken) {
    this.refreshToken = refreshToken;
  }

}
