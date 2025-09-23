package com.project.hiuni.domain.schedule.repository;

import com.project.hiuni.domain.schedule.entity.Schedule;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ScheduleRepository extends JpaRepository <Schedule, Long> {

  List<Schedule> findAllByUserId(Long userId);

  @Query("select s from Schedule s "
      + "where s.user.id = :userId "
      + "and s.startDate <= :endDate "
      + "and s.endDate >= :startDate")
  List<Schedule> findAllByUserIdAndDate(Long userId, LocalDateTime startDate, LocalDateTime endDate);
}
