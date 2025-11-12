package com.project.hiuni.domain.record.resume.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class CustomInvalidException extends CustomException {

  public CustomInvalidException(ErrorCode errorCode) {
    super(errorCode);
  }
}
