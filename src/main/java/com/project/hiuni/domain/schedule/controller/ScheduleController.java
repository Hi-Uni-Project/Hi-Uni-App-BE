package com.project.hiuni.domain.schedule.controller;

import com.project.hiuni.domain.schedule.dto.response.CategoryResponse;
import com.project.hiuni.domain.schedule.repository.CategoryRepository;
import com.project.hiuni.global.common.dto.response.ResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/schedules")
public class ScheduleController {

  private final CategoryRepository categoryRepository;


  @GetMapping("/categories")
  public ResponseDTO<List<CategoryResponse>> getCategories() {

    List<CategoryResponse> response = categoryRepository.findAll();

    return ResponseDTO.of(response, "카테고리 조회 성공");
  }



}
