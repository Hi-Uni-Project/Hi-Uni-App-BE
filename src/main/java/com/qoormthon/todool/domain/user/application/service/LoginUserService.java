package com.qoormthon.todool.domain.user.application.service;

import com.qoormthon.todool.domain.user.adapter.dto.response.LoginUserResponseDto;
import com.qoormthon.todool.domain.user.application.command.LoginUserCommand;
import com.qoormthon.todool.domain.user.application.port.in.LoginUserUseCase;
import com.qoormthon.todool.domain.user.application.port.out.ExistsByUserPort;
import com.qoormthon.todool.domain.user.application.port.out.FindUserPort;
import com.qoormthon.todool.domain.user.domain.model.User;
import com.qoormthon.todool.domain.user.exception.UserInvalidValueException;
import com.qoormthon.todool.global.error.exception.ErrorCode;
import com.qoormthon.todool.global.security.util.JWTutil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class LoginUserService implements LoginUserUseCase {

    private final JWTutil jwtUtil;
    private final ExistsByUserPort existsByUserPort;
    private final FindUserPort findUserPort;
    private final BCryptPasswordEncoder hash;

    @Autowired
    public LoginUserService(JWTutil jwtUtil, ExistsByUserPort existsByUserPort, BCryptPasswordEncoder hash
    , FindUserPort findUserPort) {
        this.jwtUtil = jwtUtil;
        this.existsByUserPort = existsByUserPort;
        this.hash = hash;
        this.findUserPort = findUserPort;
    }

    @Override
    public LoginUserResponseDto login(LoginUserCommand loginUserCommand) {
        if(existsByUserPort.existsByUserId(loginUserCommand.getUserId())) { //사용자가 존재하는지 확인
            User user = findUserPort.findByUserId(loginUserCommand.getUserId()); //사용자 정보 가져오기
            if(hash.matches(loginUserCommand.getPassword(), user.getPassword())) { //비밀번호 확인
                String accessToken = jwtUtil.createAccessToken(loginUserCommand.getUserId(), "USER");
                String refreshToken = jwtUtil.createRefreshToken(loginUserCommand.getUserId(), "USER");

                return LoginUserResponseDto.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
            } else { //비밀번호가 틀렸을 경우
                log.info("[LoginUserService] 로그인 실패, 비밀번호가 일치하지 않습니다.");
                throw new UserInvalidValueException(ErrorCode.INVALID_CREDENTIALS);
            }
        } else { //사용자가 존재하지 않을 경우
            log.info("[LoginUserService] 로그인 실패, 존재하지 않는 사용자입니다.");
            throw new UserInvalidValueException(ErrorCode.INVALID_CREDENTIALS);
        }
    }
}
