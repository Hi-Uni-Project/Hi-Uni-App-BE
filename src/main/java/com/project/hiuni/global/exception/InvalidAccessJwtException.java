package com.project.hiuni.global.exception;

public class InvalidAccessJwtException extends CustomException {

  public InvalidAccessJwtException(ErrorCode errorCode) {
    super(errorCode);
  }
}
