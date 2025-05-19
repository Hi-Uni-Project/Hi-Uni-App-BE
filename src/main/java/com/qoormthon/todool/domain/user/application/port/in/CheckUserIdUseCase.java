package com.qoormthon.todool.domain.user.application.port.in;

import com.qoormthon.todool.domain.user.adapter.dto.response.CheckUserIdResponseDto;

public interface CheckUserIdUseCase {
    CheckUserIdResponseDto checkUserId(String userId);
}
