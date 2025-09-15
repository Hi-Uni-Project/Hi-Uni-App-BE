package com.project.hiuni.global.exception;

public class ExpiredAccessJwtException extends CustomException {

  public ExpiredAccessJwtException(ErrorCode errorCode) {
    super(errorCode);
  }
}
