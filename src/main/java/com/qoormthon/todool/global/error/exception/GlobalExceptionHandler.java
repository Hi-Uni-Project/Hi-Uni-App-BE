package com.qoormthon.todool.global.error.exception;


import com.qoormthon.todool.domain.user.exception.*;
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
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ResponseDto.response(e.getErrorCode().getHttpStatus(), e.getMessage(), false));
    }

    @ExceptionHandler(UserFindException.class)
    public ResponseEntity<?> UserFindException(UserFindException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ResponseDto.response(e.getErrorCode().getHttpStatus(), e.getMessage(), null));
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<?> InvalidCredentialsException(InvalidCredentialsException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ResponseDto.response(e.getErrorCode().getHttpStatus(), e.getMessage(), null));
    }

    @ExceptionHandler(UserDuplicatedException.class)
    public ResponseEntity<?> UserDuplicatedException(UserDuplicatedException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ResponseDto.response(e.getErrorCode().getHttpStatus(), e.getMessage(), null));
    }

    @ExceptionHandler(InvalidFileTypeException.class)
    public ResponseEntity<?> InvalidFileTypeException(InvalidFileTypeException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ResponseDto.response(e.getErrorCode().getHttpStatus(), e.getMessage(), null));
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<?> InternalServerException(InternalServerException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ResponseDto.response(e.getErrorCode().getHttpStatus(), e.getMessage(), null));
    }

    @ExceptionHandler(SelfAccessOnlyException.class)
    public ResponseEntity<?> SelfAccessOnlyException(SelfAccessOnlyException e) {
        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(ResponseDto.response(e.getErrorCode().getHttpStatus(), e.getMessage(), null));
    }
}
