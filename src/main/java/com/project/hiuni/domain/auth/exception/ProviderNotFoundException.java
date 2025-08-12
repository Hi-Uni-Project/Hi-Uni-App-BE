package com.project.hiuni.domain.auth.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class ProviderNotFoundException extends CustomException {

  public ProviderNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
