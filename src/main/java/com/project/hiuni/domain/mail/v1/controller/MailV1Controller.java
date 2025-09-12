package com.project.hiuni.domain.mail.v1.controller;

import com.project.hiuni.domain.mail.dto.request.CodeRequest;
import com.project.hiuni.domain.mail.dto.request.MailRequest;
import com.project.hiuni.domain.mail.dto.response.CodeResponse;
import com.project.hiuni.domain.mail.dto.response.MailResponse;
import com.project.hiuni.domain.mail.exception.InvalidEmailFormatException;
import com.project.hiuni.domain.mail.v1.service.MailV1Service;
import com.project.hiuni.global.common.dto.response.ResponseDTO;
import com.project.hiuni.global.exception.ErrorCode;
import com.project.hiuni.global.exception.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mail")
public class MailV1Controller {

  private final MailV1Service mailV1Service;

  @PostMapping("/send")
  public ResponseDTO<String> sendMail(@RequestBody @Valid MailRequest mailRequest,
      BindingResult bindingResult, HttpServletRequest httpServletRequest) {

    if(bindingResult.hasErrors()) {
      throw new ValidationException(ErrorCode.INVALID_INPUT_VALUE);
    }

    boolean emailIsValid = mailV1Service.validateEmail(mailRequest.getEmail());

    if(!emailIsValid) {
      throw new InvalidEmailFormatException(ErrorCode.INVALID_EMAIL_FORMAT);
    }

    mailV1Service.sendMail(mailRequest, httpServletRequest);

    return ResponseDTO.of("인증 메일이 전송되었습니다.");
  }

  @PostMapping("/validate-email")
  public MailResponse validateEmail(@RequestBody @Valid MailRequest mailRequest,
  BindingResult bindingResult) {

    if(bindingResult.hasErrors()) {
      throw new ValidationException(ErrorCode.INVALID_INPUT_VALUE);
    }

    boolean emailIsValid = mailV1Service.validateEmail(mailRequest.getEmail());

    if(!emailIsValid) {
      throw new InvalidEmailFormatException(ErrorCode.INVALID_EMAIL_FORMAT);
    }

    return MailResponse
        .builder()
        .message("유효한 이메일 형식입니다.")
        .build();
  }

  @PostMapping("/validate-code")
  public ResponseDTO<String> validateCode(@RequestBody @Valid CodeRequest codeRequest,
      HttpServletRequest httpServletRequest, BindingResult bindingResult) {

    if(bindingResult.hasErrors()) {
      throw new ValidationException(ErrorCode.INVALID_INPUT_VALUE);
    }

    if(!mailV1Service.validateCode(codeRequest, httpServletRequest)) {
      throw new InvalidEmailFormatException(ErrorCode.INVALID_EMAIL_CODE);
    }

    return ResponseDTO.of("이메일 인증에 성공하였습니다.");
  }
}
