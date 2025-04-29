package com.qoormthon.todool.domain.mail.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MailSendDto {

    @Schema(example = "tempId")
    private String userId;

    @Schema(example = "20211476")
    private String stdNo;

    @Schema(example = "대진대학교")
    private String univName;

}
