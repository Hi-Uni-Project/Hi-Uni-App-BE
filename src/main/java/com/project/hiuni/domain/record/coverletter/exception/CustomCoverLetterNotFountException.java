package com.project.hiuni.domain.record.coverletter.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class CustomCoverLetterNotFountException extends CustomException {

  public CustomCoverLetterNotFountException(ErrorCode errorCode) {
    super(errorCode);
  }
}
