package com.project.hiuni.domain.univ.v1.controller;

import com.project.hiuni.domain.univ.dto.UnivDataDto.School;
import com.project.hiuni.domain.univ.v1.docs.UnivV1Docs;
import com.project.hiuni.domain.univ.v1.service.UnivV1Service;
import com.project.hiuni.global.exception.ErrorCode;
import com.project.hiuni.global.exception.ValidationException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 대학교 목록 검색 및 조회 API 입니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/univs")
public class UnivV1Controller implements UnivV1Docs {

  private final UnivV1Service univV1Service;


  /**
   * 전체 대학교 목록을 조회합니다.
   * @return List<School> 전체 대학교 목록
   */
  @Override
  @GetMapping
  public List<School> findAllSchools() {
    return univV1Service.findAllSchools();
  }


  /**
   * 대학교 이름으로 검색합니다.
   * @param keyword 대학교 이름 검색어
   * @return List<School> 검색된 대학교 목록
   * @throws ValidationException 검색어가 비어있거나 2글자 미만인 경우 / '대학', '대학교', '학교' 가 만 검색했으면 예외처리
   */
  @Override
  @GetMapping("/search")
  public List<School> findSchoolsByUnivName(@RequestParam("keyword") String keyword) {

    // 1. 검색어가 비어있거나 2글자 미만인 경우 예외 처리
    if(keyword.isBlank() || keyword.length() < 2) {
      throw new ValidationException(ErrorCode.INVALID_SEARCH_KEYWORD_LENGTH);
    }

    // 2. 검색어가 '대학', '대학교', '학교' 라면 예외처리
    if(keyword.equals("대학") || keyword.equals("대학교") || keyword.equals("학교")) {
      throw new ValidationException(ErrorCode.INVALID_SEARCH_KEYWORD);
    }

    return univV1Service.findSchoolsByUnivName(keyword);
  }




}
