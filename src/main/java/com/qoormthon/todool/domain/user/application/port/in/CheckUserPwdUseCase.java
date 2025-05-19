package com.qoormthon.todool.domain.user.application.port.in;

import com.qoormthon.todool.domain.user.adapter.dto.response.CheckUserPwdResponseDto;

public interface CheckUserPwdUseCase {
    CheckUserPwdResponseDto checkUserPwd(String userPwd);
}
