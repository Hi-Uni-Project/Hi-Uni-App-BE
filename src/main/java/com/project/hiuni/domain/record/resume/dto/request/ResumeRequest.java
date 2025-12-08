package com.project.hiuni.domain.record.resume.dto.request;

import com.project.hiuni.domain.record.resume.achievement.dto.AchievementDto;
import com.project.hiuni.domain.record.resume.career.dto.CareerDto;
import com.project.hiuni.domain.record.resume.education.dto.EducationDto;
import com.project.hiuni.domain.record.resume.entity.Gender;
import com.project.hiuni.domain.record.resume.entity.Resume;
import com.project.hiuni.domain.record.resume.language.dto.LanguageDto;
import com.project.hiuni.domain.record.resume.language.entity.Level;
import com.project.hiuni.domain.record.resume.link.dto.LinkDto;
import com.project.hiuni.domain.record.resume.project.dto.ProjectDto;
import com.project.hiuni.domain.record.resume.skill.entity.Skill;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResumeRequest {

  private String name; //이름

  private Gender gender; //성별

  private Integer birthYear; //출생년도

  private String title; //이력서 제목

  private String aboutMe; //내 소개

  private Boolean updateImage; //이미지 변경 여부

  private List<CareerDto> careers; //경력 사항

  private List<ProjectDto> projects; //프로젝트 사항

  private List<EducationDto> educations; //학력 사항

  private List<SkillDto> skills;

  private List<LanguageDto> languages; //어학

  private List<AchievementDto> achievements; //수상/자격증/교육

  private List<LinkDto> links; //링크

  @Getter
  @Setter
  public static class SkillDto {
    private Long skillId;

    public Skill toEntity(Resume resume) {
      return Skill.of(
          this.skillId,
          resume
      );
    }

  }

}
