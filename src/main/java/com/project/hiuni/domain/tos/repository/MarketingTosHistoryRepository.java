package com.project.hiuni.domain.tos.repository;

import com.project.hiuni.domain.tos.entity.MarketingTosHistory;
import com.project.hiuni.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarketingTosHistoryRepository extends JpaRepository<MarketingTosHistory, Long> {
  boolean existsByUser(User user);
}
