package com.qoormthon.todool.domain.user.application.port.in;

import com.qoormthon.todool.domain.user.adapter.dto.response.LoginUserResponseDto;
import com.qoormthon.todool.domain.user.application.command.LoginUserCommand;

public interface LoginUserUseCase {
    LoginUserResponseDto login(LoginUserCommand loginUserCommand);
}
