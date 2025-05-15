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

                // +. 4자 ~ 12자 사이
                if(userId.length() < 4 || userId.length() > 12) {
                    return ResponseEntity.badRequest()
                            .body(ResponseDto
                                    .response(HttpStatus.BAD_REQUEST, "id 값은 4자 이상 12자 이하로 입력해주세요.", userId));
                }

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
                if (containsEmoji(userId)) {
                    return ResponseEntity.badRequest()
                            .body(ResponseDto
                                    .response(HttpStatus.BAD_REQUEST, "이모티콘은 사용할 수 없습니다.", userId));
                }

                // 5. 길이는 12자 이내
//                if(userId.length() > 12) {
//                    return ResponseEntity.badRequest()
//                            .body(ResponseDto
//                                    .response(HttpStatus.BAD_REQUEST, "길이는 12자 이내여야 합니다.", userId));
//                }

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
                if (containsEmoji(userId) || userId.length() < 4 || userId.length() > 12 || userId.matches(".*[A-Z].*") || userId.contains(" ") || userId.matches(".*[!@#$%^&*()\\-_=+\\[\\]{}|;:'\",.<>/?].*")
                         || userId.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣].*")) {
                    return false;
                }
                return true;
            }

        } catch (Exception e) {
            log.error("err : " + e.getMessage());
            return false;
        }
    }

    public ResponseEntity<?> checkUserPwd(String userPwd) {
        try {

            // 1. 6자~18자 사이
            if(userPwd.length() < 6 || userPwd.length() > 18) {
                return ResponseEntity.badRequest()
                        .body(ResponseDto
                                .response(HttpStatus.BAD_REQUEST, "패스워드는 6자 이상 18자 이하로 입력해주세요.", userPwd));
            }

            // 2. 영문 소문자 최소 1자 이상 포함
            if(!userPwd.matches(".*[a-z].*")){
                return ResponseEntity.badRequest()
                        .body(ResponseDto
                                .response(HttpStatus.BAD_REQUEST, "영문 소문자가 최소 1자 이상 포함되어야 합니다.", userPwd));
            }

            //3. 영문 대문자 최소 1자 이상 포함
            if(!userPwd.matches(".*[A-Z].*")){
                return ResponseEntity.badRequest()
                        .body(ResponseDto
                                .response(HttpStatus.BAD_REQUEST, "영문 대문자가 최소 1자 이상 포함되어야 합니다.", userPwd));
            }

            //4. 특수문자 최소 1자 이상 포함
            if (!userPwd.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
                return ResponseEntity.badRequest()
                        .body(ResponseDto
                                .response(HttpStatus.BAD_REQUEST, "특수문자가 최소 1자 이상 포함되어야 합니다.", userPwd));
            }

            //5. 공백 금지
            if(userPwd.contains(" ")){
                return ResponseEntity.badRequest()
                        .body(ResponseDto
                                .response(HttpStatus.BAD_REQUEST, "공백은 포함될 수 없습니다.", userPwd));
            }

            //6. 이모티콘 금지
            if (containsEmoji(userPwd)) {
                return ResponseEntity.badRequest()
                        .body(ResponseDto
                                .response(HttpStatus.BAD_REQUEST, "이모티콘은 사용할 수 없습니다.", userPwd));
            }

            //------------------------------------------------------------------

            return ResponseEntity.ok()
                    .body(ResponseDto.response(HttpStatus.OK, "사용 가능한 패스워드 입니다.", userPwd));

        } catch (Exception e) {
            log.error("err : " + e.getMessage());
            return ResponseEntity.internalServerError()
                    .body(ResponseDto
                            .response(HttpStatus.INTERNAL_SERVER_ERROR, "알 수 없는 오류입니다.", userPwd));
        }
    }

    public boolean booleanCheckUserPwd(String userPwd) {
        try {
            if(userPwd.length() < 6 || userPwd.length() > 18 || !userPwd.matches(".*[a-z].*") || !userPwd.matches(".*[A-Z].*")
            || !userPwd.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*") || userPwd.contains(" ") || containsEmoji(userPwd)) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            log.error("err : " + e.getMessage());
            return false;
        }
    }




    // 이모지 검증 메서드
    private boolean containsEmoji(String text) {
        return text.codePoints().anyMatch(codePoint -> {
            return (codePoint >= 0x1F600 && codePoint <= 0x1F64F) || // 이모티콘
                    (codePoint >= 0x1F300 && codePoint <= 0x1F5FF) || // 기호 및 그림 문자
                    (codePoint >= 0x1F680 && codePoint <= 0x1F6FF) || // 교통 및 지도 기호
                    (codePoint >= 0x1F1E6 && codePoint <= 0x1F1FF) || // 국기
                    (codePoint >= 0x2600 && codePoint <= 0x26FF) ||   // 기타 기호
                    (codePoint >= 0x2700 && codePoint <= 0x27BF) ||   // 딩뱃
                    (codePoint >= 0x1F900 && codePoint <= 0x1F9FF) || // 보충 기호 및 그림 문자
                    (codePoint >= 0x1F018 && codePoint <= 0x1F0F5) || // 추가 이모티콘
                    (codePoint >= 0x1F200 && codePoint <= 0x1F2FF);   // 보충 기호 및 그림 문자
        });
    }
}
