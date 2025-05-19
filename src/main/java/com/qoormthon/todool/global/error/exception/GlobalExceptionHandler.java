package com.qoormthon.todool.global.error.exception;


import com.qoormthon.todool.domain.user.exception.UserInvalidValueException;
import com.qoormthon.todool.global.common.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UserInvalidValueException.class)
    public ResponseEntity<?> UserInvalidValueException(UserInvalidValueException e) {
        return ResponseEntity.status(ErrorCode.INVALID_INPUT_VALUE.getHttpStatus())
                .body(ResponseDto.response(ErrorCode.INVALID_INPUT_VALUE.getHttpStatus(), e.getMessage(), false));
    }
}
