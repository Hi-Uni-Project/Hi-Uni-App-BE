package com.project.hiuni.global.common.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * BaseTime 클래스는 JPA 엔티티의 생성 및 수정 시간을 자동으로 기록하기 위한 추상 클래스입니다. 이 클래스를 상속받는 엔티티들은 created_date와
 * updated_date 필드를 포함하게 됩니다.
 */

@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class BaseTime {

  @CreatedDate
  private LocalDateTime created_date;

  @LastModifiedDate
  private LocalDateTime updated_date;
}
