package com.project.hiuni.domain.user.entity;

import com.project.hiuni.global.common.entity.BaseTime;
import com.project.hiuni.global.security.core.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity

/**
 * BaseTime 클래스를 상속받아 생성 및 수정 시간을 자동으로 기록합니다
 */
public class UserEntity extends BaseTime {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(unique = true, nullable = false)
  private String socialEmail;

  @Column(nullable = false)
  private String socialProvider;

  @Column(nullable = false)
  private String univName;

  private String majorName;

  @Column(nullable = false)
  private String univEmail;

  @Column(unique = true, nullable = false)
  private String nickname;

  private String imageUrl;

  @Enumerated(EnumType.STRING)
  private Role role;


}
