package com.project.hiuni.domain.mail.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class MessaginSendException extends CustomException {

  public MessaginSendException(ErrorCode errorCode) {
    super(errorCode);
  }
}
