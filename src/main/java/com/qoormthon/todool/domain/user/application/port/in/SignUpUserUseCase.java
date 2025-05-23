package com.qoormthon.todool.domain.user.application.port.in;

import com.qoormthon.todool.domain.user.adapter.dto.response.SignUpUserResponseDto;
import com.qoormthon.todool.domain.user.application.command.SignUpUserCommand;
import org.springframework.web.multipart.MultipartFile;

public interface SignUpUserUseCase {
    SignUpUserResponseDto signUp(SignUpUserCommand signUpUserCommand, MultipartFile file);
}
