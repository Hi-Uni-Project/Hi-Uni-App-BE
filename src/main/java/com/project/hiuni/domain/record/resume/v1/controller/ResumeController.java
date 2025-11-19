package com.project.hiuni.domain.record.resume.v1.controller;

import com.project.hiuni.domain.record.dto.response.RecordOverviewResponse;
import com.project.hiuni.domain.record.dto.response.RecordOverviewResponse.CoverLetterResponse;
import com.project.hiuni.domain.record.resume.dto.response.ResumeResponse;
import com.project.hiuni.global.common.dto.response.ResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 내 이력서 화면에 보여질 데이터를 제공하는 컨트롤러 입니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/resume/")
public class ResumeController {

  //TODO: 목 데이터를 반환하는 API 입니다. 추후에 서비스 로직이 구현되면 수정이 필요합니다.
  @GetMapping
  public ResponseDTO<ResumeResponse> getResume() {


  }

}
