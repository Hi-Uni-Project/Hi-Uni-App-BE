package com.project.hiuni.domain.tos.repository;

import com.project.hiuni.domain.tos.entity.InPersonTosHistory;
import com.project.hiuni.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InPersonTosHistoryRepository extends JpaRepository<InPersonTosHistory, Long> {
  boolean existsByUser(User user);
}
