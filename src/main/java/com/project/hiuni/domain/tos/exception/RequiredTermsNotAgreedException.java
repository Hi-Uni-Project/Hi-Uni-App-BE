package com.project.hiuni.domain.tos.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class RequiredTermsNotAgreedException extends CustomException {

  public RequiredTermsNotAgreedException(ErrorCode errorCode) {
    super(errorCode);
  }
}
