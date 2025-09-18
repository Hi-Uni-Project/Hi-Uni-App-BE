package com.project.hiuni.global.exception;

public class InvalidRefrashJwtException extends CustomException {

  public InvalidRefrashJwtException(ErrorCode errorCode) {
    super(errorCode);
  }
}
