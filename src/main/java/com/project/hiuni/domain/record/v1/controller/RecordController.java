package com.project.hiuni.domain.record.v1.controller;

import com.project.hiuni.domain.record.dto.response.RecordOverviewResponse;
import com.project.hiuni.domain.record.dto.response.RecordOverviewResponse.CoverLetterResponse;
import com.project.hiuni.domain.record.v1.service.RecordService;
import com.project.hiuni.global.common.dto.response.ResponseDTO;
import com.project.hiuni.global.security.core.CustomUserDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 내 기록 메인화면에 보여질 데이터를 제공하는 컨트롤러 입니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/record/")
public class RecordController {

  private final RecordService recordService;

  @GetMapping("/overview")
  public ResponseDTO<RecordOverviewResponse> getRecordOverview(
      @AuthenticationPrincipal CustomUserDetails userDetails
  ) {

    RecordOverviewResponse response = recordService.getRecordOverview(userDetails.getId());

    return ResponseDTO.of(response, "조회에 성공하였습니다.");
  }




}
