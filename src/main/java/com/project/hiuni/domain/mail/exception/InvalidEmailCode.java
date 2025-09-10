package com.project.hiuni.domain.mail.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class InvalidEmailCode extends CustomException {

  public InvalidEmailCode(ErrorCode errorCode) {
    super(errorCode);
  }
}
