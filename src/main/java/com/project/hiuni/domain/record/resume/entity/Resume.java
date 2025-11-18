package com.project.hiuni.domain.record.resume.entity;

import com.project.hiuni.domain.record.resume.career.entity.Career;
import com.project.hiuni.domain.record.resume.exception.CustomInvalidException;
import com.project.hiuni.domain.record.resume.project.entity.Project;
import com.project.hiuni.domain.user.entity.User;
import com.project.hiuni.global.exception.ErrorCode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Entity
@Getter
@Slf4j
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "resume")
public class Resume {

  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @Enumerated(EnumType.STRING)
  private Gender gender;

  private Integer birthYear;

  private String title;

  @Lob
  private String aboutMe;

  private Integer aboutMeCnt; //AI 내 소개 생성 가능 횟수

  public String imageUrl;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  public User user;

  @OneToMany(mappedBy = "resume")
  private List<Career> careers = new ArrayList<>();

  @OneToMany(mappedBy = "resume")
  private List<Project> projects = new ArrayList<>();

  /**
   * AI 내 소개 생성 가능 횟수 감소 메서드 입니다.
   * @throws RuntimeException 잔여 횟수가 존재하지 않을 때 감소를 시도할 경우 발생합니다.
   */
  public void decreaseCnt() {
    if (this.aboutMeCnt < 0) {
      log.info("잔여 횟수가 존재하지 않지만, 감소를 시도했습니다.");
      throw new CustomInvalidException(ErrorCode.INTERNAL_SERVER_ERROR);
    }
    this.aboutMeCnt -= 1;
  }

}
