package com.project.hiuni.global.exception;

public class NotFoundInfoException extends CustomException {

  public NotFoundInfoException(ErrorCode errorCode) {
    super(errorCode);
  }
}
