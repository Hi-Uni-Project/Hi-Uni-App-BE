package com.qoormthon.todool.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {
    @Schema(example = "20251444")
    private String stdNo; //학번 pk

    @Schema(example = "your_password")
    private String password; //비밀번호
}
