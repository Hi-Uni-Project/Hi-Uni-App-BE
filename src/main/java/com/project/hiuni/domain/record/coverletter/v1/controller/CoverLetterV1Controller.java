package com.project.hiuni.domain.record.coverletter.v1.controller;

import com.project.hiuni.domain.record.coverletter.dto.request.AiCoverLetterRequest;
import com.project.hiuni.domain.record.coverletter.dto.request.CoverLetterRequest;
import com.project.hiuni.domain.record.coverletter.dto.response.AiCoverLetterResponse;
import com.project.hiuni.domain.record.coverletter.dto.response.CoverLetterResponse;
import com.project.hiuni.domain.record.coverletter.v1.service.CoverLetterV1Service;
import com.project.hiuni.global.common.dto.response.ResponseDTO;
import com.project.hiuni.global.security.core.CustomUserDetails;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cover-letter")
public class CoverLetterV1Controller {
  private final CoverLetterV1Service coverLetterV1Service;

  @GetMapping
  public ResponseDTO<List<CoverLetterResponse>> getCoverLetter(
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    List<CoverLetterResponse> response = coverLetterV1Service.getCoverLetter(userDetails.getId());

    return ResponseDTO.of(response, "자기소개서 문항 조회에 성공하였습니다.");
  }

  @PostMapping
  public ResponseDTO<?> createCoverLetter(
      @RequestBody List<CoverLetterRequest> request,
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    coverLetterV1Service.createCoverLetter(request, userDetails.getId());

    return ResponseDTO.of("자기소개서 생성에 성공하였습니다.");
  }


  @PostMapping("/ai-cover-letter")
  public ResponseDTO<AiCoverLetterResponse> getAiCoverLetter(
      @RequestBody AiCoverLetterRequest request,
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    AiCoverLetterResponse response = coverLetterV1Service.getAiCoverLetter(request, userDetails.getId());

    return ResponseDTO.of(response, "AI 자기소개서 생성에 성공하였습니다.");

  }






}
