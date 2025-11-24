package com.project.hiuni.domain.record.resume.skill.v1.controller;


import com.project.hiuni.domain.record.resume.dto.response.ResumeResponse;
import com.project.hiuni.domain.record.resume.skill.dto.SkillDataDto;
import com.project.hiuni.domain.record.resume.skill.entity.Skill;
import com.project.hiuni.domain.record.resume.skill.repository.SkillDataRepository;
import com.project.hiuni.domain.record.resume.skill.v1.service.SkillService;
import com.project.hiuni.global.common.dto.response.ResponseDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 이력서 스킬 목록을 조회할 수 있는 컨트롤러 입니다.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/skill")
public class SkillController {

  private final SkillDataRepository skillDataRepository;

  private final SkillService skillService;

  @GetMapping("/all")
  public ResponseDTO<List<SkillDataDto>> getAllSkills() {
    List<SkillDataDto> skills = skillDataRepository.findAll();

    return ResponseDTO.of(skills, "스킬 목록 조회에 성공하였습니다.");
  }

  @GetMapping("/search")
  public ResponseDTO<List<SkillDataDto>> searchSkills(@RequestParam("keyword") String keyword) {

    List<SkillDataDto> response = skillService.findSkillsByName(keyword);

    return ResponseDTO.of(response, "스킬 목록 조회에 성공하였습니다.");
  }

}
