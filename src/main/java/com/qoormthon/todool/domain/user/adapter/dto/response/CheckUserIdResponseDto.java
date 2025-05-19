package com.qoormthon.todool.domain.user.adapter.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CheckUserIdResponseDto {

    private String message;
    private boolean isValid;

}
