package com.project.hiuni.global.exception;

public class ValidationException extends CustomException {

  public ValidationException(ErrorCode errorCode) {
    super(errorCode);
  }
}
