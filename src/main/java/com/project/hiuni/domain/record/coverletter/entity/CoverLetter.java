package com.project.hiuni.domain.record.coverletter.entity;

import com.project.hiuni.domain.record.coverletter.dto.response.CoverLetterResponse;
import com.project.hiuni.domain.user.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Table(name = "cover_letter")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CoverLetter {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String question;

  @Lob
  private String answer;

  @Setter
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @Builder
  private CoverLetter(Long id, String question, String answer, User user) {
    this.id = id;
    this.question = question;
    this.answer = answer;
    this.user = user;
  }

  public static CoverLetter of(Long id, String question, String answer, User user) {
    return CoverLetter
        .builder()
        .id(id)
        .question(question)
        .answer(answer)
        .user(user)
        .build();
  }

  public CoverLetterResponse.CoverLetters toCoverLetterResponse() {
    return CoverLetterResponse.CoverLetters.
        builder()
        .coverLetterId(this.id)
        .question(this.question)
        .answer(this.answer)
        .build();
  }
}
