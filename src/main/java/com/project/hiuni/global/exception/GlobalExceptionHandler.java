package com.project.hiuni.global.exception;


import com.project.hiuni.domain.auth.exception.GoogleInvalidTokenException;
import com.project.hiuni.domain.auth.exception.KakaoInvalidTokenException;
import com.project.hiuni.domain.auth.exception.NaverInvalidTokenException;
import com.project.hiuni.domain.auth.exception.ProviderNotFoundException;
import com.project.hiuni.domain.mail.exception.InvalidEmailCodeException;
import com.project.hiuni.domain.mail.exception.InvalidEmailFormatException;
import com.project.hiuni.domain.post.exception.CustomForbiddenException;
import com.project.hiuni.domain.post.exception.CustomPostNotFoundException;
import com.project.hiuni.domain.tos.exception.RequiredTermsNotAgreedException;
import com.project.hiuni.global.common.dto.response.ErrorResponse;
import com.project.hiuni.global.common.dto.response.ResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(ProviderNotFoundException.class)
  public ResponseEntity<ResponseDTO> providerNotFoundException(ProviderNotFoundException e) {
    return ResponseEntity.status(e.getErrorCode().getActualStatusCode())
        .body(ResponseDTO.of(e.getErrorCode()));
  }

  @ExceptionHandler(ValidationException.class)
  public ResponseEntity<ResponseDTO> providerNotFoundException(ValidationException e) {
    return ResponseEntity.status(e.getErrorCode().getActualStatusCode())
        .body(ResponseDTO.of(e.getErrorCode()));
  }

  @ExceptionHandler(InternalServerException.class)
  public ResponseEntity<ResponseDTO> internalServerException(InternalServerException e) {
    return ResponseEntity.status(e.getErrorCode().getActualStatusCode())
        .body(ResponseDTO.of(e.getErrorCode()));
  }

  @ExceptionHandler(GoogleInvalidTokenException.class)
  public ResponseEntity<ResponseDTO> GoogleInvalidTokenException(GoogleInvalidTokenException e) {
    return ResponseEntity.status(e.getErrorCode().getActualStatusCode())
        .body(ResponseDTO.of(e.getErrorCode()));
  }

  @ExceptionHandler(InvalidEmailFormatException.class)
  public ResponseEntity<ResponseDTO> InvalidEmailFormatException(InvalidEmailFormatException e) {
    return ResponseEntity.status(e.getErrorCode().getActualStatusCode())
        .body(ResponseDTO.of(e.getErrorCode()));
  }

  @ExceptionHandler(InvalidEmailCodeException.class)
  public ResponseEntity<ResponseDTO> InvalidEmailCodeException(InvalidEmailCodeException e) {
    return ResponseEntity.status(e.getErrorCode().getActualStatusCode())
        .body(ResponseDTO.of(e.getErrorCode()));
  }

  @ExceptionHandler(RequiredTermsNotAgreedException.class)
  public ResponseEntity<ResponseDTO> RequiredTermsNotAgreedException(RequiredTermsNotAgreedException e) {
    return ResponseEntity.status(e.getErrorCode().getActualStatusCode())
        .body(ResponseDTO.of(e.getErrorCode()));
  }

  @ExceptionHandler(CustomPostNotFoundException.class)
  public ResponseEntity<ResponseDTO> CustomPostNotFoundException(CustomPostNotFoundException e) {
    return ResponseEntity.status(e.getErrorCode().getActualStatusCode())
        .body(ResponseDTO.of(e.getErrorCode()));
  }

  @ExceptionHandler(CustomForbiddenException.class)
  public ResponseEntity<ResponseDTO> CustomForbiddenException(CustomForbiddenException e) {
    return ResponseEntity.status(e.getErrorCode().getActualStatusCode())
        .body(ResponseDTO.of(e.getErrorCode()));
  }

  @ExceptionHandler(KakaoInvalidTokenException.class)
  public ResponseEntity<ResponseDTO> KakaoInvalidTokenException(KakaoInvalidTokenException e) {
    return ResponseEntity.status(e.getErrorCode().getActualStatusCode())
        .body(ResponseDTO.of(e.getErrorCode()));
  }

  @ExceptionHandler(NaverInvalidTokenException.class)
  public ResponseEntity<ResponseDTO> NaverInvalidTokenException(NaverInvalidTokenException e) {
    return ResponseEntity.status(e.getErrorCode().getActualStatusCode())
        .body(ResponseDTO.of(e.getErrorCode()));
  }

  @ExceptionHandler(TokenInvalidType.class)
  public ResponseEntity<ResponseDTO> TokenInvalidType(TokenInvalidType e) {
    return ResponseEntity.status(e.getErrorCode().getActualStatusCode())
        .body(ResponseDTO.of(e.getErrorCode()));
  }

}
