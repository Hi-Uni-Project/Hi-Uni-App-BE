package com.project.hiuni.global.exception;


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

}
