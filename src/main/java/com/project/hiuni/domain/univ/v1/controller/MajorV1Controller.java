package com.project.hiuni.domain.univ.v1.controller;

import com.project.hiuni.domain.univ.dto.MajorDataDto.Major;
import com.project.hiuni.domain.univ.v1.service.MajorV1Service;
import com.project.hiuni.global.common.dto.response.ResponseDTO;
import com.project.hiuni.global.exception.ErrorCode;
import com.project.hiuni.global.exception.ValidationException;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 대학별 학과조회 api 입니다.
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/majors")
public class MajorV1Controller {

  private final MajorV1Service majorV1Service;

  @GetMapping("/{univName}/search")
  public ResponseDTO<List<Major>> findMajorsByUnivName(@RequestParam("keyword") String keyword, @PathVariable String univName) {

    // 1. 검색어가 비어있거나 2글자 미만인 경우 예외 처리
    if(keyword.isBlank() || keyword.length() < 2) {
      throw new ValidationException(ErrorCode.INVALID_SEARCH_KEYWORD_LENGTH);
    }

    List<Major> response = majorV1Service.findMajorsByUnivName(univName, keyword);

    return ResponseDTO.of(response, "학과 검색에 성공하였습니다.");
  }

  @GetMapping("/{univName}")
  public ResponseDTO<List<Major>> findAllMajorsByUnivName(@PathVariable String univName) {

    List<Major> response = majorV1Service.findMajorsByUnivName(univName, "");
    return ResponseDTO.of(response, "학과 검색에 성공하였습니다.");
  }



}
