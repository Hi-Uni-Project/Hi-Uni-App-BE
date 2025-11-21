package com.project.hiuni.domain.tos.repository;

import com.project.hiuni.domain.tos.entity.ServiceImprovementTosHistory;
import com.project.hiuni.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceImprovementTosHistoryRepository extends JpaRepository<ServiceImprovementTosHistory, Long> {
  boolean existsByUser(User user);
}
