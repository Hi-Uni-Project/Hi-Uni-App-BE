package com.qoormthon.todool.domain.user.application.service;

import com.qoormthon.todool.domain.user.adapter.dto.response.FindUserResponseDto;
import com.qoormthon.todool.domain.user.application.port.in.FindUserUseCase;
import com.qoormthon.todool.domain.user.application.port.out.ExistsByUserPort;
import com.qoormthon.todool.domain.user.application.port.out.FindUserPort;
import com.qoormthon.todool.domain.user.exception.SelfAccessOnlyException;
import com.qoormthon.todool.domain.user.exception.UserFindException;
import com.qoormthon.todool.domain.user.exception.UserInvalidValueException;
import com.qoormthon.todool.domain.user.mapper.UserMapper;
import com.qoormthon.todool.global.error.exception.ErrorCode;
import com.qoormthon.todool.global.security.util.JWTutil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FindUserService implements FindUserUseCase {

    private final JWTutil jwTutil;
    private final ExistsByUserPort existsByUserPort;
    private final FindUserPort findUserPort;
    private final UserMapper userMapper;

    @Autowired
    public FindUserService(JWTutil jwTutil, ExistsByUserPort existsByUserPort,
                           FindUserPort findUserPort, UserMapper userMapper) {
        this.jwTutil = jwTutil;
        this.existsByUserPort = existsByUserPort;
        this.findUserPort = findUserPort;
        this.userMapper = userMapper;
    }

    @Override
    public FindUserResponseDto userFind(String userId, HttpServletRequest request) {
            if(userId == null || userId.isEmpty()) {
                throw new UserInvalidValueException(ErrorCode.INVALID_INPUT_VALUE);
            }

            if(!jwTutil.getUserId(jwTutil.extractToken(request)).equals(userId)){
                throw new SelfAccessOnlyException(ErrorCode.SELF_ACCESS_ONLY);
            }

            if(!existsByUserPort.existsByUserId(userId)){
                throw new UserFindException(ErrorCode.USER_NOT_FOUND);
            }

            FindUserResponseDto findUserResponseDto = userMapper.userToFindUserResponseDto(findUserPort.findByUserId(userId));

        return findUserResponseDto;
    }
}
