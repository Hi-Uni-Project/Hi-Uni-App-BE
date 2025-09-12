package com.project.hiuni.domain.tos.controller;

import com.project.hiuni.domain.tos.dto.request.TosRequest;
import com.project.hiuni.domain.tos.service.TosService;
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
@RequestMapping("/api/v1/tos")
public class TosController {

  private final TosService tosService;

  @PostMapping("/agree")
  public ResponseDTO<?> agreeTos(@RequestBody @Valid TosRequest tosRequest,
      BindingResult bindingResult, HttpServletRequest httpServletRequest) {

    if(bindingResult.hasErrors()) {
      throw new ValidationException(ErrorCode.INVALID_INPUT_VALUE);
    }

    tosService.agreeTos(tosRequest, httpServletRequest);

    return ResponseDTO.of("약관 동의에 성공하였습니다.");

  }



}
