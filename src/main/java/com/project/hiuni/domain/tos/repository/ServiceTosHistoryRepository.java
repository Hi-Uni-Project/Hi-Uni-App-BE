package com.project.hiuni.domain.tos.repository;

import com.project.hiuni.domain.tos.entity.ServiceTosHistory;
import com.project.hiuni.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ServiceTosHistoryRepository extends JpaRepository<ServiceTosHistory, Long> {
  boolean existsByUser(User user);

  @Modifying
  @Query("delete from ServiceTosHistory s where s.user = :user")
  void deleteAllByUser(User user);
}
