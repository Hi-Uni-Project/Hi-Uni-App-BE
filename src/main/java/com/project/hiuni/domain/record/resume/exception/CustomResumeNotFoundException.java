package com.project.hiuni.domain.record.resume.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class CustomResumeNotFoundException extends CustomException {

  public CustomResumeNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
