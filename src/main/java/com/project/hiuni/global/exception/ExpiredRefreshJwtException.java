package com.project.hiuni.global.exception;

public class ExpiredRefreshJwtException extends CustomException {

  public ExpiredRefreshJwtException(ErrorCode errorCode) {
    super(errorCode);
  }
}
