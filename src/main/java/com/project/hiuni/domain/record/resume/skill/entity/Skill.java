package com.project.hiuni.domain.record.resume.skill.entity;

import com.project.hiuni.domain.record.resume.entity.Resume;
import com.project.hiuni.domain.record.resume.skill.dto.SkillDataDto;
import com.project.hiuni.domain.record.resume.skill.repository.SkillDataRepository;
import com.project.hiuni.global.exception.ErrorCode;
import com.project.hiuni.global.exception.InternalServerException;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Getter
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "skill")
public class Skill {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long skillDataId;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "resume_id")
  private Resume resume;

  @Builder
  private Skill(Long skillDataId, Resume resume) {
    this.skillDataId = skillDataId;
    this.resume = resume;
  }

  public static Skill of(Long skillDataId, Resume resume) {
    return Skill.builder()
        .skillDataId(skillDataId)
        .resume(resume)
        .build();
  }

  public void update(Skill skill) {
    this.skillDataId = skill.getSkillDataId();
  }

  public SkillDataDto toDto(SkillDataRepository skillDataRepository) {

    SkillDataDto skilldataDto = skillDataRepository.findById(this.skillDataId)
        .orElseThrow(() -> new InternalServerException(ErrorCode.INTERNAL_SERVER_ERROR));

    return skilldataDto;

  }
}
