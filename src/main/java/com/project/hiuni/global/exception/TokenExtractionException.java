package com.project.hiuni.global.exception;

public class TokenExtractionException extends CustomException {

  public TokenExtractionException(ErrorCode errorCode) {
    super(errorCode);
  }
}
