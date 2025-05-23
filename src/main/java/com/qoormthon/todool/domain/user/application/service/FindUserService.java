package com.qoormthon.todool.domain.user.application.service;

import com.qoormthon.todool.domain.user.adapter.dto.response.FindUserResponseDto;
import com.qoormthon.todool.domain.user.application.port.in.FindUserUseCase;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class FindUserService implements FindUserUseCase {

    @Override
    public FindUserResponseDto userFind(String userId, HttpServletRequest request) {
//            if(userId == null || userId.isEmpty()) {
//                throw new UserFindException("유저 아이디를 입력해주세요.");
//            }
//
//            if(!jwTutil.getUserId(jwTutil.extractToken(request)).equals(userId)){
//                throw new UserFindException("본인만 조회할 수 있습니다.");
//            }
//
//            if(!userRepository.existsByUserId(userId)) {
//                throw new UserFindException("존재하지 않는 유저입니다.");
//            }
//
//            return userMapper.userToUserFindResponseDto(findUserPort.findByUserId(userId));
        return null;
    }
}
