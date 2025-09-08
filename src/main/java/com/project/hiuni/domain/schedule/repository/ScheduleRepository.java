package com.project.hiuni.domain.schedule.repository;

import com.project.hiuni.domain.schedule.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository <Schedule, Long> {
}
