package com.qoormthon.todool.domain.user.adapter.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignUpUserResponseDto {
    private String accessToken;
    private String refreshToken;
}
