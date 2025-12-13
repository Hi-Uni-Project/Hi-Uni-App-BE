package com.project.hiuni.domain.tos.repository;

import com.project.hiuni.domain.tos.entity.MarketingTosHistory;
import com.project.hiuni.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MarketingTosHistoryRepository extends JpaRepository<MarketingTosHistory, Long> {
  boolean existsByUser(User user);
  @Modifying
  @Query("delete from MarketingTosHistory m where m.user = :user")
  void deleteAllByUser(User user);
}
