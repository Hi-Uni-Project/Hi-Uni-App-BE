package com.project.hiuni.domain.univ.v1.service;

import com.project.hiuni.domain.univ.dto.UnivDataDto;
import com.project.hiuni.domain.univ.dto.UnivDataDto.School;
import com.project.hiuni.domain.univ.dto.MajorDataDto;
import com.project.hiuni.global.exception.ErrorCode;
import com.project.hiuni.global.exception.InternalServerException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UnivV1Service {

  private final UnivDataDto univDataDto;
  private final MajorDataDto majorDataDto;

  /**
   * 전체 대학교 목록을 조회합니다.
   * @return List<School> 전체 대학교 목록
   * @throws InternalServerException 학교 목록 조회 중 에러 발생 시 예외처리
   */
  public List<School> findAllSchools() {

    try {

      return univDataDto.getRecords();

    } catch (Exception e) {
      log.error("학교 목록 조회 중 에러 발생 :" + e.getMessage());
      throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

  }



  /**
   * 대학교 이름으로 검색합니다.
   * @param keyword 대학교 이름 검색어
   * @return List<School> 검색된 대학교 목록
   * @throws InternalServerException 학교 목록 조회 중 에러 발생 시 예외처리
   */
  public List<School> findSchoolsByUnivName(String keyword) {
    try {

      List<School> searchedSchools = univDataDto.getRecords().stream()
          .filter(school -> school.getUnivName().contains(keyword))
          .toList();
      return searchedSchools;

    } catch (Exception e) {
      log.error("학교 목록 조회 중 에러 발생 :" + e.getMessage());
      throw new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR);
    }
  }




}
