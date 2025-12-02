package com.project.hiuni.global.exception;


import com.project.hiuni.domain.auth.exception.GoogleInvalidTokenException;
import com.project.hiuni.domain.auth.exception.KakaoInvalidTokenException;
import com.project.hiuni.domain.auth.exception.NaverInvalidTokenException;
import com.project.hiuni.domain.auth.exception.ProviderNotFoundException;
import com.project.hiuni.domain.mail.exception.InvalidEmailCodeException;
import com.project.hiuni.domain.mail.exception.InvalidEmailFormatException;
import com.project.hiuni.domain.post.exception.CustomForbiddenException;
import com.project.hiuni.domain.post.exception.CustomPostNotFoundException;
import com.project.hiuni.domain.record.coverletter.exception.CustomCoverLetterNotFountException;
import com.project.hiuni.domain.record.exception.InsufficientGenerationCountException;
import com.project.hiuni.domain.record.resume.exception.CustomInvalidException;
import com.project.hiuni.domain.record.resume.exception.CustomResumeNotFoundException;
import com.project.hiuni.domain.schedule.exception.CustomScheduleNotFoundException;
import com.project.hiuni.domain.tos.exception.RequiredTermsNotAgreedException;
import com.project.hiuni.domain.user.exception.CustomUserNotFoundException;
import com.project.hiuni.global.common.dto.response.ResponseDTO;
import com.project.hiuni.infra.claude.exception.AiCallException;
import jakarta.validation.ConstraintViolationException;
import java.time.format.DateTimeParseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

  @ExceptionHandler(NotFoundInfoException.class)
  public ResponseEntity<ResponseDTO> NotFoundInfoException(NotFoundInfoException e) {
    return ResponseEntity.status(e.getErrorCode().getActualStatusCode())
        .body(ResponseDTO.of(e.getErrorCode()));
  }

  @ExceptionHandler(HttpMessageNotReadableException.class)
  public ResponseEntity<ResponseDTO> HttpMessageNotReadableException(HttpMessageNotReadableException e) {

    log.error("요청 본문을 파싱할 수 없음. : " + e.getMessage());

    return ResponseEntity.status(ErrorCode.INVALID_INPUT_VALUE.getActualStatusCode())
        .body(ResponseDTO.of(ErrorCode.INVALID_INPUT_VALUE));
  }

  @ExceptionHandler(DateTimeParseException.class)
  public ResponseEntity<ResponseDTO> DateTimeParseException(DateTimeParseException e) {

    log.error("요청 본문을 파싱할 수 없음. / 요청 시간이 포맷에 안맞거나 공백이거나 Null임 : " + e.getMessage());

    return ResponseEntity.status(ErrorCode.INVALID_INPUT_VALUE.getActualStatusCode())
        .body(ResponseDTO.of(ErrorCode.INVALID_INPUT_VALUE));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ResponseDTO> MethodArgumentNotValidException(MethodArgumentNotValidException e) {
    String customMessage = e.getBindingResult()
            .getFieldErrors()
            .stream()
            .findFirst()
            .map(FieldError::getDefaultMessage)
            .orElse(ErrorCode.INVALID_INPUT_VALUE.getMessage());

    return ResponseEntity.status(ErrorCode.INVALID_INPUT_VALUE.getActualStatusCode())
            .body(ResponseDTO.error(ErrorCode.INVALID_INPUT_VALUE, customMessage));
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<ResponseDTO> ConstraintViolationException(ConstraintViolationException e) {
    String customMessage = e.getConstraintViolations()
            .stream()
            .findFirst()
            .map(v -> v.getMessage())
            .orElse(ErrorCode.INVALID_INPUT_VALUE.getMessage());

    return ResponseEntity.status(ErrorCode.INVALID_INPUT_VALUE.getActualStatusCode())
            .body(ResponseDTO.error(ErrorCode.INVALID_INPUT_VALUE, customMessage));
  }

  @ExceptionHandler(CustomUserNotFoundException.class)
  public ResponseEntity<ResponseDTO> CustomUserNotFoundException(CustomUserNotFoundException e) {
    return ResponseEntity.status(e.getErrorCode().getActualStatusCode())
        .body(ResponseDTO.of(e.getErrorCode()));
  }

  @ExceptionHandler(CustomScheduleNotFoundException.class)
  public ResponseEntity<ResponseDTO> CustomScheduleNotFoundException(CustomScheduleNotFoundException e) {
    return ResponseEntity.status(e.getErrorCode().getActualStatusCode())
        .body(ResponseDTO.of(e.getErrorCode()));
  }

  @ExceptionHandler(CustomInvalidException.class)
  public ResponseEntity<ResponseDTO> CustomInvalidException(CustomInvalidException e) {
    return ResponseEntity.status(e.getErrorCode().getActualStatusCode())
        .body(ResponseDTO.of(e.getErrorCode()));
  }

  @ExceptionHandler(AiCallException.class)
  public ResponseEntity<ResponseDTO> CustomInvalidException(AiCallException e) {
    return ResponseEntity.status(e.getErrorCode().getActualStatusCode())
        .body(ResponseDTO.of(e.getErrorCode()));
  }

  @ExceptionHandler(InsufficientGenerationCountException.class)
  public ResponseEntity<ResponseDTO> InsufficientGenerationCountException(InsufficientGenerationCountException e) {
    return ResponseEntity.status(e.getErrorCode().getActualStatusCode())
        .body(ResponseDTO.of(e.getErrorCode()));
  }

  @ExceptionHandler(CustomResumeNotFoundException.class)
  public ResponseEntity<ResponseDTO> CustomResumeNotFoundException(CustomResumeNotFoundException e) {
    return ResponseEntity.status(e.getErrorCode().getActualStatusCode())
        .body(ResponseDTO.of(e.getErrorCode()));
  }

  @ExceptionHandler(CustomCoverLetterNotFountException.class)
  public ResponseEntity<ResponseDTO> CustomCoverLetterNotFountException(CustomCoverLetterNotFountException e) {
    return ResponseEntity.status(e.getErrorCode().getActualStatusCode())
        .body(ResponseDTO.of(e.getErrorCode()));
  }





}
