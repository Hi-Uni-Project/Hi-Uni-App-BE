package com.qoormthon.todool.domain.mail.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailAuthenticationDto {

    @Schema(example = "20211476")
    private String stdNo;

    @Schema(example = "000000")
    private String authenticationCode;

}
