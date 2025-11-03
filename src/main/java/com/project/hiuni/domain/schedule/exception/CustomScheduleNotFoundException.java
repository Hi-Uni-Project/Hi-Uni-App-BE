package com.project.hiuni.domain.schedule.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class CustomScheduleNotFoundException extends CustomException {

  public CustomScheduleNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
