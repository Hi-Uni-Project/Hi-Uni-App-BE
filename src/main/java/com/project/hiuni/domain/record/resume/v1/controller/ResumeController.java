package com.project.hiuni.domain.record.resume.v1.controller;


import com.project.hiuni.domain.record.resume.achievement.dto.AchievementDto;
import com.project.hiuni.domain.record.resume.achievement.entity.Type;
import com.project.hiuni.domain.record.resume.career.dto.CareerDto;
import com.project.hiuni.domain.record.resume.dto.request.ResumeRequest;
import com.project.hiuni.domain.record.resume.dto.response.AiAboutMeResponse;
import com.project.hiuni.domain.record.resume.dto.response.ResumeResponse;
import com.project.hiuni.domain.record.resume.education.dto.EducationDto;
import com.project.hiuni.domain.record.resume.education.entity.GraduationStatus;
import com.project.hiuni.domain.record.resume.entity.Gender;
import com.project.hiuni.domain.record.resume.language.dto.LanguageDto;
import com.project.hiuni.domain.record.resume.language.entity.Level;
import com.project.hiuni.domain.record.resume.link.dto.LinkDto;
import com.project.hiuni.domain.record.resume.project.dto.ProjectDto;
import com.project.hiuni.domain.record.resume.skill.repository.SkillDataRepository;
import com.project.hiuni.domain.record.resume.v1.service.ResumeService;
import com.project.hiuni.global.common.dto.response.ResponseDTO;
import com.project.hiuni.global.security.core.CustomUserDetails;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 내 이력서 화면에 보여질 데이터를 제공하는 컨트롤러 입니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/resume")
public class ResumeController {

  private final SkillDataRepository skillDataRepository;

  private final ResumeService resumeService;

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseDTO<?> createOrUpdate(
      @RequestPart(value = "image", required = false) MultipartFile file,
      @RequestPart("data") ResumeRequest request,
      @AuthenticationPrincipal CustomUserDetails userDetails) {

    resumeService.createOrUpdateResume(file, request, userDetails.getId());

    return ResponseDTO.of("이력서 저장에 성공하였습니다.");
  }

  @GetMapping("/ai-about-me")
  public ResponseDTO<AiAboutMeResponse> getAiAboutMe(@AuthenticationPrincipal CustomUserDetails userDetails) {

    AiAboutMeResponse response = resumeService.getAiAboutMe(userDetails.getId());

    return ResponseDTO.of(response, "AI 자기소개 생성에 성공하였습니다.");
  }

  @GetMapping
  public ResponseDTO<ResumeResponse> getResume(@AuthenticationPrincipal CustomUserDetails userDetails) {

    ResumeResponse response = resumeService.getResume(userDetails.getId());

    return ResponseDTO.of(response, "조회에 성공하였습니다.");
  }

}
