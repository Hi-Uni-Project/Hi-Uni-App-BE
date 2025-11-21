package com.project.hiuni.infra.claude.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class AiCallException extends CustomException {

  public AiCallException(ErrorCode errorCode) {
    super(errorCode);
  }
}
