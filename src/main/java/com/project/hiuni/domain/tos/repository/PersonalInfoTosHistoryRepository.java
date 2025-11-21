package com.project.hiuni.domain.tos.repository;

import com.project.hiuni.domain.tos.entity.PersonalInfoTosHistory;
import com.project.hiuni.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalInfoTosHistoryRepository extends JpaRepository<PersonalInfoTosHistory, Long> {
  boolean existsByUser(User user);
}
