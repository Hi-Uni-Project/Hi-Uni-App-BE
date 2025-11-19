package com.project.hiuni.domain.record.resume.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.hiuni.domain.record.resume.achievement.dto.response.AchievementResponse;
import com.project.hiuni.domain.record.resume.career.dto.response.CareerResponse;
import com.project.hiuni.domain.record.resume.career.entity.Career;
import com.project.hiuni.domain.record.resume.education.dto.response.EducationResponse;
import com.project.hiuni.domain.record.resume.entity.Gender;
import com.project.hiuni.domain.record.resume.language.dto.response.LanguageResponse;
import com.project.hiuni.domain.record.resume.link.dto.response.LinkResponse;
import com.project.hiuni.domain.record.resume.project.dto.response.ProjectResponse;
import com.project.hiuni.domain.record.resume.skill.dto.SkillDataDto;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ResumeResponse {
  private String name; //이름

  private Gender gender; //성별

  private Integer birthYear; //출생년도

  private String imageUrl; //프로필 이미지 URL

  private String title; //이력서 제목

  private String aboutMe; //내 소개

  private Integer aboutMeCnt; //내 소개 생성가능 횟수

  private List<CareerResponse> careers; //경력사항
  @JsonProperty("careersSize")
  public int getCareersSize() {
    return careers != null ? careers.size() : 0;
  }

  private List<ProjectResponse> projects; //프로젝트
  @JsonProperty("projectsSize")
  public int getProjectsSize() {
    return projects != null ? projects.size() : 0;
  }

  private List<EducationResponse> educations; //학력
  @JsonProperty("educationsSize")
  public int getEducationsSize() {
    return educations != null ? educations.size() : 0;
  }

  private List<LanguageResponse> languages; //어학
  @JsonProperty("languagesSize")
  public int getLanguagesSize() {
    return languages != null ? languages.size() : 0;
  }

  private List<AchievementResponse> achievements; //수상/자격증/교육
  @JsonProperty("achievementsSize")
  public int getAchievementsSize() {
    return achievements != null ? achievements.size() : 0;
  }

  private List<LinkResponse> links; //링크
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
