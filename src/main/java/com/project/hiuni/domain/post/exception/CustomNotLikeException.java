package com.project.hiuni.domain.post.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class CustomNotLikeException extends CustomException {
    public CustomNotLikeException(ErrorCode errorCode) {
      super(errorCode);
    }
}
