package com.project.hiuni.domain.calender.repository;

import com.project.hiuni.domain.calender.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalenderRepository extends JpaRepository<Schedule, Long> {

}
