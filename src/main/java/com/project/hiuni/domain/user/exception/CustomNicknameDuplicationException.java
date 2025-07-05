package com.project.hiuni.domain.user.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class CustomNicknameDuplicationException extends CustomException {

  public CustomNicknameDuplicationException(ErrorCode errorCode) {
    super(errorCode);
  }

}
