package com.project.hiuni.domain.auth.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class GoogleInvalidTokenException extends CustomException {

  public GoogleInvalidTokenException(ErrorCode errorCode) {
    super(errorCode);
  }
}
