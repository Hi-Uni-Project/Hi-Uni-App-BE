package com.project.hiuni.domain.record.coverletter.dto.request;

import com.project.hiuni.domain.record.coverletter.entity.CoverLetter;
import com.project.hiuni.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoverLetterRequest {
  private String question;
  private String answer;

  public CoverLetter toEntity(User user) {
    return CoverLetter.builder()
        .question(this.question)
        .answer(this.answer)
        .user(user)
        .build();
  }
}
