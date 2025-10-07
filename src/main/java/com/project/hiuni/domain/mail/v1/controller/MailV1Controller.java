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
import java.util.HashMap;
import java.util.Objects;
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
  public ResponseDTO<HashMap<String, Object>> sendMail(@RequestBody @Valid MailRequest mailRequest,
      BindingResult bindingResult, HttpServletRequest httpServletRequest) {

    if(bindingResult.hasErrors()) {
      throw new ValidationException(ErrorCode.INVALID_INPUT_VALUE);
    }

    boolean emailIsValid = mailV1Service.validateEmail(mailRequest.getEmail(), mailRequest.getUnivName());

    if(!emailIsValid) {
      throw new InvalidEmailFormatException(ErrorCode.INVALID_EMAIL_FORMAT);
    }

    String authMailId = mailV1Service.sendMail(mailRequest);

    HashMap<String, Object> response = new HashMap<>();
    response.put("authMailId", authMailId);

    return ResponseDTO.of(response, "인증 메일이 전송되었습니다.");
  }

  @PostMapping("/validate-email")
  public ResponseDTO<String> validateEmail(@RequestBody @Valid MailRequest mailRequest,
  BindingResult bindingResult) {

    if(bindingResult.hasErrors()) {
      throw new ValidationException(ErrorCode.INVALID_INPUT_VALUE);
    }

    boolean emailIsValid = mailV1Service.validateEmail(mailRequest.getEmail(), mailRequest.getUnivName());

    if(!emailIsValid) {
      throw new InvalidEmailFormatException(ErrorCode.INVALID_EMAIL_FORMAT);
    }


    return ResponseDTO.of("유효한 이메일 형식입니다.");
  }

  @PostMapping("/validate-code")
  public ResponseDTO<String> validateCode(@RequestBody @Valid CodeRequest codeRequest,
      BindingResult bindingResult) {

    if(bindingResult.hasErrors()) {
      throw new ValidationException(ErrorCode.INVALID_INPUT_VALUE);
    }

    if(!mailV1Service.validateCode(codeRequest)) {
      throw new InvalidEmailFormatException(ErrorCode.INVALID_EMAIL_CODE);
    }

    return ResponseDTO.of("이메일 인증에 성공하였습니다.");
  }
}
