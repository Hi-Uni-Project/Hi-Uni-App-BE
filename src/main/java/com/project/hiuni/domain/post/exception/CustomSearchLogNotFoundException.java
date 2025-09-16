package com.project.hiuni.domain.post.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class CustomSearchLogNotFoundException extends CustomException {

    public CustomSearchLogNotFoundException(ErrorCode errorCode) {
      super(errorCode);
    }
}
