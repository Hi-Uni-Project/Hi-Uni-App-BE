package com.project.hiuni.domain.auth.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class NaverInvalidTokenException extends CustomException {

  public NaverInvalidTokenException(ErrorCode errorCode) {
    super(errorCode);
  }
}
