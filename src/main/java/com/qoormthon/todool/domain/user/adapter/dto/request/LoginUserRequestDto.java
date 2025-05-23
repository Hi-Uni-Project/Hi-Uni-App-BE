package com.qoormthon.todool.domain.user.adapter.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUserRequestDto {
    @Schema(example = "tempid")
    private String userId; //아이디 pk

    @Schema(example = "Temp1234!")
    private String password; //비밀번호
}
