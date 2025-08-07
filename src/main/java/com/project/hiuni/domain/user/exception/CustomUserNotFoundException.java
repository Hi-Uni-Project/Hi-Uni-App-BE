package com.project.hiuni.domain.user.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class CustomUserNotFoundException extends CustomException {

  public CustomUserNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }

}
