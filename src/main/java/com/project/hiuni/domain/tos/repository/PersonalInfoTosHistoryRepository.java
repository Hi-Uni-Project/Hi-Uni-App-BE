package com.project.hiuni.domain.tos.repository;

import com.project.hiuni.domain.tos.entity.PersonalInfoTosHistory;
import com.project.hiuni.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface PersonalInfoTosHistoryRepository extends JpaRepository<PersonalInfoTosHistory, Long> {
  boolean existsByUser(User user);
  @Modifying
  @Query("delete from PersonalInfoTosHistory p where p.user = :user")
  void deleteAllByUser(User user);
}
