package com.qoormthon.todool.domain.user.application.port.in;

import com.qoormthon.todool.domain.user.adapter.dto.response.FindUserResponseDto;
import jakarta.servlet.http.HttpServletRequest;

public interface FindUserUseCase {
    FindUserResponseDto userFind(String userId, HttpServletRequest request);
}
