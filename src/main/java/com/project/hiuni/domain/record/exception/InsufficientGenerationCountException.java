package com.project.hiuni.domain.record.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class InsufficientGenerationCountException extends CustomException {

  public InsufficientGenerationCountException(ErrorCode errorCode) {
    super(errorCode);
  }
}
