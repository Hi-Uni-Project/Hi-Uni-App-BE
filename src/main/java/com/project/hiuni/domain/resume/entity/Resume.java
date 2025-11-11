package com.project.hiuni.domain.resume.entity;

import com.project.hiuni.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
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

  /**
   * AI 내 소개 생성 가능 횟수 감소 메서드 입니다.
   * @throws RuntimeException 잔여 횟수가 존재하지 않을 때 감소를 시도할 경우 발생합니다.
   */
  public void decreaseCnt() {
    if (this.aboutMeCnt < 0) {
      throw new RuntimeException("잔여 횟수가 존재하지 않지만, 감소를 시도했습니다.");
    }
    this.aboutMeCnt -= 1;
  }

}
