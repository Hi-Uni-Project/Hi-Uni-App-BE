package com.project.hiuni.domain.mail.v1.controller;

import com.project.hiuni.domain.mail.dto.request.MailRequest;
import com.project.hiuni.domain.mail.dto.response.MailResponse;
import com.project.hiuni.domain.mail.v1.service.MailV1Service;
import com.project.hiuni.global.exception.ErrorCode;
import com.project.hiuni.global.exception.ValidationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.net.http.HttpRequest;
import java.util.Map;
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
  public MailResponse sendMail(@RequestBody @Valid MailRequest mailRequest,
      BindingResult bindingResult, HttpServletRequest httpServletRequest) {

    if(bindingResult.hasErrors()) {
      throw new ValidationException(ErrorCode.INVALID_INPUT_VALUE);
    }

    mailV1Service.sendMail(mailRequest, httpServletRequest);
    return MailResponse
        .builder()
        .message("인증 메일이 전송되었습니다.")
        .build();
  }





}
