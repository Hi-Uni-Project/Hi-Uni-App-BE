package com.project.hiuni.domain.tos.repository;

import com.project.hiuni.domain.tos.entity.ServiceTosHistory;
import com.project.hiuni.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceTosHistoryRepository extends JpaRepository<ServiceTosHistory, Long> {
  boolean existsByUser(User user);
}
