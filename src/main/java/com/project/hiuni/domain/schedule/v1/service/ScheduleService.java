package com.project.hiuni.domain.schedule.v1.service;

import com.project.hiuni.domain.schedule.dto.CategoryDataDto;
import com.project.hiuni.domain.schedule.dto.request.ScheduleRequest;
import com.project.hiuni.domain.schedule.dto.request.UpdateScheduleRequest;
import com.project.hiuni.domain.schedule.dto.response.ScheduleResponse;
import com.project.hiuni.domain.schedule.dto.response.ScheduleResponse.Category;
import com.project.hiuni.domain.schedule.entity.Schedule;
import com.project.hiuni.domain.schedule.exception.CustomScheduleNotFoundException;
import com.project.hiuni.domain.schedule.repository.CategoryRepository;
import com.project.hiuni.domain.schedule.repository.ScheduleRepository;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.domain.user.exception.CustomUserNotFoundException;
import com.project.hiuni.domain.user.repository.UserRepository;
import com.project.hiuni.global.exception.ErrorCode;
import com.project.hiuni.global.security.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
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
  private final CategoryRepository categoryRepository;

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
  public void updateSchedule(Long scheduleId, Long userId, UpdateScheduleRequest request) {
    User user = userRepository.findById(userId).orElseThrow(
        () -> new CustomUserNotFoundException(ErrorCode.USER_NOT_FOUND)
    );

    Schedule schedule = scheduleRepository.findById(scheduleId).orElseThrow(
        () -> new CustomScheduleNotFoundException(ErrorCode.SCHEDULE_NOT_FOUND)
    );

    CategoryDataDto category = categoryRepository.findById(request.getCategoryId()).orElseThrow(

    );



  }

  @Transactional
  public List<ScheduleResponse> getSchedulesByDate(HttpServletRequest httpServletRequest,
      String startDate, String endDate) {

    User user = jwtTokenProvider.getUserFromRequest(httpServletRequest);

    LocalDateTime startDateTime = LocalDate.parse(startDate).atStartOfDay();
    LocalDateTime endDateTime = LocalDate.parse(endDate).atTime(23, 59, 59, 999999999);

    List<Schedule> schedules = scheduleRepository.findAllByUserIdAndDate(
        user.getId(),
        startDateTime,
        endDateTime
    );

    List<ScheduleResponse> response = schedules.stream()
        .map(sc -> {

          CategoryDataDto category = categoryRepository.findById(sc.getCategoryId());

          String categoryName = category.getCategoryname();
          String backgroundColor = category.getBackgroundcolor();
          String textColor = category.getTextcolor();

          String StartDateTime = sc.getStartDate().
              format(DateTimeFormatter.ofPattern("a hh:mm", Locale.ENGLISH));
          String EndDateTime = sc.getEndDate()
              .format(DateTimeFormatter.ofPattern("a hh:mm",Locale.ENGLISH));

          String time = StartDateTime + " - " + EndDateTime;


          ScheduleResponse scheduleResponse = ScheduleResponse
              .builder()
              .scheduleId(sc.getId())
              .startDate(sc.getStartDate())
              .endDate(sc.getEndDate())
              .category(
                  ScheduleResponse.Category.builder()
                      .categoryId(sc.getCategoryId())
                      .categoryName(categoryName)
                      .backgroundColor(backgroundColor)
                      .textColor(textColor)
                      .build()
              )
              .detail(sc.getTitle())
              .time(time)
              .memo(sc.getMemo())
              .build();

          return scheduleResponse;
        })
        .toList();

    return response;
  }


}
