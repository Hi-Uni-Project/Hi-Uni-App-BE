package com.project.hiuni.domain.record.resume.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.hiuni.domain.record.resume.achievement.dto.AchievementDto;
import com.project.hiuni.domain.record.resume.career.dto.CareerDto;
import com.project.hiuni.domain.record.resume.education.dto.EducationDto;
import com.project.hiuni.domain.record.resume.entity.Gender;
import com.project.hiuni.domain.record.resume.language.dto.LanguageDto;
import com.project.hiuni.domain.record.resume.link.dto.LinkDto;
import com.project.hiuni.domain.record.resume.project.dto.ProjectDto;
import com.project.hiuni.domain.record.resume.skill.dto.SkillDataDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResumeResponse {

  private Long resumeId;

  private String name; //이름

  private Gender gender; //성별

  private Integer birthYear; //출생년도

  private String imageUrl; //프로필 이미지 URL

  private String title; //이력서 제목

  private String aboutMe; //내 소개

  private Integer aboutMeCnt; //내 소개 생성가능 횟수

  private List<CareerDto> careers; //경력사항
  @JsonProperty("careersSize")
  public int getCareersSize() {
    return careers != null ? careers.size() : 0;
  }

  private List<ProjectDto> projects; //프로젝트
  @JsonProperty("projectsSize")
  public int getProjectsSize() {
    return projects != null ? projects.size() : 0;
  }

  private List<EducationDto> educations; //학력
  @JsonProperty("educationsSize")
  public int getEducationsSize() {
    return educations != null ? educations.size() : 0;
  }

  private List<LanguageDto> languages; //어학
  @JsonProperty("languagesSize")
  public int getLanguagesSize() {
    return languages != null ? languages.size() : 0;
  }

  private List<AchievementDto> achievements; //수상/자격증/교육
  @JsonProperty("achievementsSize")
  public int getAchievementsSize() {
    return achievements != null ? achievements.size() : 0;
  }

  private List<LinkDto> links; //링크
  @JsonProperty("linksSize")
  public int getLinksSize() {
    return links != null ? links.size() : 0;
  }

  private List<SkillDataDto> skills; //스킬
  @JsonProperty("skillsSize")
  public int getSkillsSize() {
    return skills != null ? skills.size() : 0;
  }


}
