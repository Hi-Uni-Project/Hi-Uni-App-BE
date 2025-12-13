package com.project.hiuni.domain.tos.repository;

import com.project.hiuni.domain.tos.entity.InPersonTosHistory;
import com.project.hiuni.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface InPersonTosHistoryRepository extends JpaRepository<InPersonTosHistory, Long> {
  boolean existsByUser(User user);
  @Modifying
  @Query("delete from InPersonTosHistory i where i.user = :user")
  void deleteAllByUser(User user);
}
