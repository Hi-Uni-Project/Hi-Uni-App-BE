package com.project.hiuni.domain.auth.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class KakaoInvalidTokenException extends CustomException {

  public KakaoInvalidTokenException(ErrorCode errorCode) {
    super(errorCode);
  }
}
