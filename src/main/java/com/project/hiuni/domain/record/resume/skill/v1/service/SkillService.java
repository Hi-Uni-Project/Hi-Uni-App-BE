package com.project.hiuni.domain.record.resume.skill.v1.service;

import com.project.hiuni.domain.record.resume.skill.dto.SkillDataDto;
import com.project.hiuni.domain.record.resume.skill.repository.SkillDataRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SkillService {

  private final SkillDataRepository skillDataRepository;

  public List<SkillDataDto> findSkillsByName(String keyword) {

    List<SkillDataDto> skills = skillDataRepository.findAll();

    List<SkillDataDto> searchedSkills = skills.stream()
        .filter(skill -> skill.getName().toLowerCase().contains(keyword.toLowerCase()))
        .toList();

    return searchedSkills;

  }

}
