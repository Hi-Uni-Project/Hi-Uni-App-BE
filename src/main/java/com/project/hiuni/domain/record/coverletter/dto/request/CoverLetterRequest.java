package com.project.hiuni.domain.record.coverletter.dto.request;

import com.project.hiuni.domain.record.coverletter.entity.CoverLetter;
import com.project.hiuni.domain.user.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoverLetterRequest {
  private Long coverLetterId;
  private String question;
  private String answer;

  public CoverLetter toEntity(User user) {
    return CoverLetter.of(
        this.coverLetterId,
        this.question,
        this.answer,
        user
    );

  }
}
