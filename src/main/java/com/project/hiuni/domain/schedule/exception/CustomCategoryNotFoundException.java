package com.project.hiuni.domain.schedule.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class CustomCategoryNotFoundException extends CustomException {

  public CustomCategoryNotFoundException(ErrorCode errorCode) {
    super(errorCode);
  }
}
