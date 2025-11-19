package com.project.hiuni.domain.record.resume.dto.response;

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

  private List<CareerResponse> careers; //경력사항
  private List<ProjectResponse> projects; //프로젝트
  private List<EducationResponse> educations; //학력
  private List<LanguageResponse> languages; //어학
  private List<AchievementResponse> achievements; //수상/자격증/교육
  private List<LinkResponse> links; //링크
  private List<SkillDataDto> skills; //스킬

}
