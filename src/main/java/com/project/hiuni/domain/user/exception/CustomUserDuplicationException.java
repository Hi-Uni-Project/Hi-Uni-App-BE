package com.project.hiuni.domain.user.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class CustomUserDuplicationException extends CustomException {

  public CustomUserDuplicationException(ErrorCode errorCode) {
    super(errorCode);
  }

}
