package com.project.hiuni.domain.tos.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "service_improvement_tos")
public class ServiceImprovementTos {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

}
