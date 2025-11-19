package com.project.hiuni.domain.record.resume.dto.response;

import com.project.hiuni.domain.record.resume.career.entity.Career;
import com.project.hiuni.domain.record.resume.entity.Gender;
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

  private List<Career> careers; //경력사항







}
