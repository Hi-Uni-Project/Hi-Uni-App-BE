package com.qoormthon.todool.domain.mail.controller;

import com.qoormthon.todool.domain.mail.dto.MailAuthenticationDto;
import com.qoormthon.todool.domain.mail.dto.MailSendDto;
import com.qoormthon.todool.domain.mail.service.MailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
@Tag(name = "인증메일 관련 api 입니다.", description = "인증 메일 전송, 검증 등의 기능입니다.")
public class MailController {

    @Autowired
    private MailService mailService;

    @Operation(summary = "인증메일 전송 api 입니다.", description = "메일 전송에는 학번과 학교명이 필요합니다. ex) 20211476 / 대진대학교 일 경우 / 20211476@daejin.ac.kr 로 전송됨")
    @PostMapping("/send")
    public ResponseEntity<?> sendMailAuthenticationCode(@RequestBody MailSendDto mailSendDto) {
        return mailService.sendMailAuthenticationCode(mailSendDto);
    }


    @ApiResponse(
            responseCode = "401",
            description = "인증 실패 JSON 응답",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            example = """
                                    {
                                       "status": "UNAUTHORIZED",
                                       "message": "인증번호가 일치하지 않습니다.",
                                       "data": "20211476"
                                     }
                                    """
                    )
            )
    )
    @ApiResponse(
            responseCode = "400",
            description = "발급된 인증번호가 없는 경우 JSON 응답",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            example = """
                                    {
                                        "status": "BAD_REQUEST",
                                        "message": "발급된 인증번호가 존재하지 않습니다.",
                                        "data": "202121476"
                                      }
                                    """
                    )
            )
    )
    @ApiResponse(
            responseCode = "200",
            description = "인증 성공 JSON 응답",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(
                            example = """
                                    {
                                        "status": "OK",
                                        "message": "인증번호가 일치합니다.",
                                        "data": "20211476"
                                      }
                                    """
                    )
            )
    )
    @Operation(summary = "메일로 전송된 인증코드를 검증하는 api 입니다.", description = "")
    @PostMapping("/valid")
    public ResponseEntity<?> MailAuthenticationCodeIsValid(@RequestBody MailAuthenticationDto mailAuthenticationDto) {
        return mailService.mailAuthenticationCodeIsValid(mailAuthenticationDto);
    }
}
