package com.project.hiuni.domain.mail.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class InvalidEmailFormatException extends CustomException {

  public InvalidEmailFormatException(ErrorCode errorCode) {
    super(errorCode);
  }
}
