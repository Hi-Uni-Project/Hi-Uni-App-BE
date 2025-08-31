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

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Column(nullable = false, length = 255)
  private String refreshToken;


  @Builder
  private Auth(Long id, User user, String refreshToken) {
    this.id = id;
    this.user = user;
    this.refreshToken = refreshToken;
  }

  public static Auth of(Long id, User user, String refreshToken) {
    return Auth.builder()
        .id(id)
        .user(user)
        .refreshToken(refreshToken)
        .build();
  }

}
