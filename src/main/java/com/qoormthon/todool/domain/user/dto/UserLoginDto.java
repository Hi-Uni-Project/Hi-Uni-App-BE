package com.qoormthon.todool.domain.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginDto {
    @Schema(example = "tempId")
    private String userId; //아이디 pk

    @Schema(example = "your_password")
    private String password; //비밀번호
}
