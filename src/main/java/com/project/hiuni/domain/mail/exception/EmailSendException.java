package com.project.hiuni.domain.mail.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class EmailSendException extends CustomException {

  public EmailSendException(ErrorCode errorCode) {
    super(errorCode);
  }
}
