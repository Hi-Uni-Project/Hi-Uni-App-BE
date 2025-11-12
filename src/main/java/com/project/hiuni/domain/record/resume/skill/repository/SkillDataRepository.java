package com.project.hiuni.domain.record.resume.skill.repository;

import com.project.hiuni.domain.record.resume.dto.SkillDataDto;
import java.util.List;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@RequiredArgsConstructor
public class SkillDataRepository {

  private final List<SkillDataDto> skillsData;

  public List<SkillDataDto> findAll() {
    return skillsData;
  }

  public Optional<SkillDataDto> findById(Long id) {
    return skillsData.stream()
        .filter(skill -> skill.getSkillId().equals(id))
        .findFirst();
  }



}
