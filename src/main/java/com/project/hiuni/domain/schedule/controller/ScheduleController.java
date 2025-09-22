package com.project.hiuni.domain.schedule.controller;

import com.project.hiuni.domain.schedule.dto.request.ScheduleRequest;
import com.project.hiuni.domain.schedule.dto.response.CategoryResponse;
import com.project.hiuni.domain.schedule.repository.CategoryRepository;
import com.project.hiuni.domain.schedule.service.ScheduleService;
import com.project.hiuni.global.common.dto.response.ResponseDTO;
import com.project.hiuni.global.exception.ErrorCode;
import com.project.hiuni.global.exception.ValidationException;
import com.project.hiuni.global.security.core.CustomUserDetails;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleController {

  private final CategoryRepository categoryRepository;
  private final ScheduleService scheduleService;


  @GetMapping("/categories")
  public ResponseDTO<List<CategoryResponse>> getCategories() {

    List<CategoryResponse> response = categoryRepository.findAll();

    return ResponseDTO.of(response, "카테고리 조회 성공");
  }

  @PostMapping
  public ResponseDTO<String> createSchedule(@RequestBody @Valid ScheduleRequest scheduleRequest,
      BindingResult bindingResult, HttpServletRequest httpServletRequest) {

    if (bindingResult.hasErrors()) {
      log.error("스케쥴 생성에 실패하였습니다. 입력값이 잘못되었습니다.");
      throw new ValidationException(ErrorCode.INVALID_INPUT_VALUE);
    }

    scheduleService.createSchedule(scheduleRequest, httpServletRequest);

    return ResponseDTO.of("스케쥴 등록에 성공하였습니다.");
  }



}
