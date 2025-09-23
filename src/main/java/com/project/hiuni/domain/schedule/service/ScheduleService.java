package com.project.hiuni.domain.schedule.service;

import com.project.hiuni.domain.schedule.dto.request.ScheduleRequest;
import com.project.hiuni.domain.schedule.dto.response.ScheduleResponse;
import com.project.hiuni.domain.schedule.entity.Schedule;
import com.project.hiuni.domain.schedule.repository.ScheduleRepository;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.exception.ErrorCode;
import com.project.hiuni.global.exception.NotFoundInfoException;
import com.project.hiuni.global.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ScheduleService {

  private final JwtTokenProvider jwtTokenProvider;

  private final UserRepository userRepository;
  private final ScheduleRepository scheduleRepository;

  @Transactional
  public void createSchedule(ScheduleRequest scheduleRequest, HttpServletRequest httpServletRequest) {

    User user = jwtTokenProvider.getUserFromRequest(httpServletRequest);

    LocalDateTime startDate = scheduleRequest.getStartDate();
    LocalDateTime endDate = scheduleRequest.getEndDate();

    Long categoryId = scheduleRequest.getCategoryId();
    String title = scheduleRequest.getDetail();
    String memo = scheduleRequest.getMemo();

    Schedule schedule = Schedule.of(
        startDate,
        endDate,
        user,
        categoryId,
        title,
        memo
    );

    scheduleRepository.save(schedule);
  }

  @Transactional
  public List<ScheduleResponse> getSchedulesByDate(HttpServletRequest httpServletRequest, String startDate, String endDate) {

    User user = jwtTokenProvider.getUserFromRequest(httpServletRequest);

  }


}
