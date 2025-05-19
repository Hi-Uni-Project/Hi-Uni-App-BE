package com.qoormthon.todool.domain.user.application.port.in;

import com.qoormthon.todool.domain.user.adapter.dto.response.UserFindResponseDto;
import jakarta.servlet.http.HttpServletRequest;

public interface FindUserUseCase {
    UserFindResponseDto userFind(String userId, HttpServletRequest request);
}
