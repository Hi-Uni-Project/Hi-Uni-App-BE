package com.project.hiuni.global.exception;


import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(InvalidValueException.class)
//    public ResponseEntity<?> UserInvalidValueException(InvalidValueException e) {
//        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
//                .body(ResponseDto.response(e.getErrorCode().getHttpStatus(), e.getMessage(), false));
//    }
}
