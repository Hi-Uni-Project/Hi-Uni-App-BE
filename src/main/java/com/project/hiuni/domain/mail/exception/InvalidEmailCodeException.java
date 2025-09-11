package com.project.hiuni.domain.mail.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class InvalidEmailCodeException extends CustomException {

  public InvalidEmailCodeException(ErrorCode errorCode) {
    super(errorCode);
  }
}
