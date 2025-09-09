package com.project.hiuni.global.exception;


import com.project.hiuni.domain.auth.exception.GoogleInvalidTokenException;
import com.project.hiuni.domain.auth.exception.ProviderNotFoundException;
import com.project.hiuni.global.common.dto.response.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ProviderNotFoundException.class)
  public ResponseEntity<ErrorResponse> providerNotFoundException(ProviderNotFoundException e) {
    return ResponseEntity.status(e.getErrorCode().getHttpStatus())
        .body(ErrorResponse.of(e.getErrorCode()));
  }

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ErrorResponse> providerNotFoundException(ValidationException e) {
    return ResponseEntity.status(e.getErrorCode().getHttpStatus())
        .body(ErrorResponse.of(e.getErrorCode()));
  }

  @ExceptionHandler(InternalServerException.class)
  public ResponseEntity<ErrorResponse> internalServerException(InternalServerException e) {
    return ResponseEntity.status(e.getErrorCode().getHttpStatus())
        .body(ErrorResponse.of(e.getErrorCode()));
  }

  @ExceptionHandler(GoogleInvalidTokenException.class)
  public ResponseEntity<ErrorResponse> GoogleInvalidTokenException(GoogleInvalidTokenException e) {
    return ResponseEntity.status(e.getErrorCode().getHttpStatus())
        .body(ErrorResponse.of(e.getErrorCode()));
  }


}
