package com.project.hiuni.global.exception;

public class InternalServerException extends CustomException {

  public InternalServerException(ErrorCode errorCode) {
    super(errorCode);
  }
}
