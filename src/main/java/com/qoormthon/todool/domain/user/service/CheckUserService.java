package com.qoormthon.todool.domain.user.service;

import com.qoormthon.todool.domain.user.repository.UserRepository;
import com.qoormthon.todool.global.common.response.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CheckUserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<?> checkUserId(String userId) {
        try {
            if(userId.isEmpty() || userId == null) {
                return ResponseEntity.badRequest()
                        .body(ResponseDto
                                .response(HttpStatus.BAD_REQUEST, "유저 아이디를 입력해주세요", userId));
            }

            if(userRepository.existsByUserId(userId)) {
                return ResponseEntity.badRequest()
                        .body(ResponseDto
                                .response(HttpStatus.BAD_REQUEST, "이미 존재하는 유저입니다.", userId));
            } else {

                //----------사용자 아이디 입력 정책에 따른 검사 필터-------------------------

                // 1. 영문 소문자만 가능 (a-z)
                if(userId.matches(".*[A-Z].*")) {
                    return ResponseEntity.badRequest()
                            .body(ResponseDto
                                    .response(HttpStatus.BAD_REQUEST, "영문 소문자만 가능합니다.", userId));
                }

                // 2. 공백은 포함될 수 없음
                if(userId.contains(" ")){
                    return ResponseEntity.badRequest()
                            .body(ResponseDto
                                    .response(HttpStatus.BAD_REQUEST, "공백은 포함될 수 없습니다.", userId));
                }

                // 3. 특수 문자는 포함될 수 없음
                if(userId.matches(".*[!@#$%^&*()\\-_=+\\[\\]{}|;:'\",.<>/?].*")) {
                    return ResponseEntity.badRequest()
                            .body(ResponseDto
                                    .response(HttpStatus.BAD_REQUEST, "특수 문자는 포함될 수 없습니다.", userId));
                }

                // 4. 이코티콘 포함될 수 없음(어떻게 검사하지..)

                // 5. 길이는 12자 이내
                if(userId.length() > 12) {
                    return ResponseEntity.badRequest()
                            .body(ResponseDto
                                    .response(HttpStatus.BAD_REQUEST, "길이는 12자 이내여야 합니다.", userId));
                }

                // 6. 한글은 포함될 수 없음
                if(userId.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣].*")) {
                    return ResponseEntity.badRequest()
                            .body(ResponseDto
                                    .response(HttpStatus.BAD_REQUEST, "한글은 포함될 수 없습니다.", userId));
                }

                //------------------------------------------------------------------

                return ResponseEntity.ok()
                        .body(ResponseDto.response(HttpStatus.OK, "사용 가능한 id 입니다.", userId));
            }

        } catch (Exception e) {
            log.error("err : " + e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(ResponseDto
                            .response(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류입니다.", userId));
        }
    }

    public boolean booleanCheckUserId(String userId) {
        try {
            if (userId.isEmpty() || userId == null) {
                return false;
            }

            if (userRepository.existsByUserId(userId)) {
                return false;
            } else {
                if (userId.matches(".*[A-Z].*") || userId.contains(" ") || userId.matches(".*[!@#$%^&*()\\-_=+\\[\\]{}|;:'\",.<>/?].*")
                        || (userId.length() > 12) || userId.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣].*")) {
                    return false;
                }
                return true;
            }

        } catch (Exception e) {
            log.error("err : " + e.getMessage());
            return false;
        }
    }
}
