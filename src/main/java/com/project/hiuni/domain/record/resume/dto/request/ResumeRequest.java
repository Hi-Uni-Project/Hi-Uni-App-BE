package com.project.hiuni.domain.record.resume.dto.request;

import com.project.hiuni.domain.record.resume.entity.Gender;
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



}
