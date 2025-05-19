package com.qoormthon.todool.domain.user.application.service;

import com.qoormthon.todool.domain.user.adapter.dto.response.CheckUserIdResponseDto;
import com.qoormthon.todool.domain.user.adapter.dto.response.CheckUserPwdResponseDto;
import com.qoormthon.todool.domain.user.adapter.out.persistence.UserRepository;
import com.qoormthon.todool.domain.user.application.port.in.CheckUserIdUseCase;
import com.qoormthon.todool.domain.user.application.port.in.CheckUserPwdUseCase;
import com.qoormthon.todool.domain.user.application.port.out.ExistsByUserPort;
import com.qoormthon.todool.domain.user.exception.UserInvalidValueException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CheckUserService implements CheckUserIdUseCase, CheckUserPwdUseCase {

    private final ExistsByUserPort existsByUserPort;

    @Autowired
    public CheckUserService(ExistsByUserPort existsByUserPort) {
        this.existsByUserPort = existsByUserPort;
    }

    @Override
    public CheckUserIdResponseDto checkUserId(String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new UserInvalidValueException("아이디는 비어있을 수 없습니다.");
        }

        if (existsByUserPort.existsByUserId(userId)) {
            throw new UserInvalidValueException("이미 사용중인 아이디 입니다.");
        }

        //----------사용자 아이디 입력 정책에 따른 검사 필터-------------------------

        // +. 4자 ~ 12자 사이
        if (userId.length() < 4 || userId.length() > 12) {
            throw new UserInvalidValueException("아이디는 4자 이상 12자 이하로 입력해주세요.");
        }

        // 1. 영문 소문자만 가능 (a-z)
        if (userId.matches(".*[A-Z].*")) {
            throw new UserInvalidValueException("영문 소문자만 가능합니다.");
        }

        // 2. 공백은 포함될 수 없음
        if (userId.contains(" ")) {
            throw new UserInvalidValueException("공백은 포함될 수 없습니다.");
        }

        // 3. 특수 문자는 포함될 수 없음
        if (userId.matches(".*[!@#$%^&*()\\-_=+\\[\\]{}|;:'\",.<>/?].*")) {
            throw new UserInvalidValueException("특수문자는 포함될 수 없습니다.");
        }

        // 4. 이코티콘 포함될 수 없음
        if (containsEmoji(userId)) {
            throw new UserInvalidValueException("이모티콘은 사용할 수 없습니다.");
        }

        // 5. 한글은 포함될 수 없음
        if (userId.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣].*")) {
            throw new UserInvalidValueException("한글은 포함될 수 없습니다.");
        }

        //------------------------------------------------------------------

        return CheckUserIdResponseDto.builder()
                .message("사용 가능한 id 입니다.")
                .isValid(true)
                .build();

    }

    public boolean booleanCheckUserId(String userId) {
        if (userId.isEmpty() || userId == null) {
            return false;
        }

        if (existsByUserPort.existsByUserId(userId)) {
            return false;
        } else {
            if (containsEmoji(userId) || userId.length() < 4 || userId.length() > 12 || userId.matches(".*[A-Z].*") || userId.contains(" ") || userId.matches(".*[!@#$%^&*()\\-_=+\\[\\]{}|;:'\",.<>/?].*")
                    || userId.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣].*")) {
                return false;
            }
            return true;
        }

    }

    @Override
    public CheckUserPwdResponseDto checkUserPwd(String userPwd) {
        // 1. 6자~18자 사이
        if (userPwd.length() < 6 || userPwd.length() > 18) {
            throw new UserInvalidValueException("패스워드는 6자 이상 18자 이하로 입력해주세요.");
        }

        // 2. 영문 소문자 최소 1자 이상 포함
        if (!userPwd.matches(".*[a-z].*")) {
            throw new UserInvalidValueException("영문 소문자가 최소 1자 이상 포함되어야 합니다.");
        }

        //3. 영문 대문자 최소 1자 이상 포함
        if (!userPwd.matches(".*[A-Z].*")) {
            throw new UserInvalidValueException("영문 대문자가 최소 1자 이상 포함되어야 합니다.");
        }

        //4. 특수문자 최소 1자 이상 포함
        if (!userPwd.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) {
            throw new UserInvalidValueException("특수문자가 최소 1자 이상 포함되어야 합니다.");
        }

        //5. 공백 금지
        if (userPwd.contains(" ")) {
            throw new UserInvalidValueException("공백은 포함될 수 없습니다.");
        }

        //6. 이모티콘 금지
        if (containsEmoji(userPwd)) {
            throw new UserInvalidValueException("이모티콘은 사용할 수 없습니다.");
        }

        // 7. 한글은 포함될 수 없음
        if (userPwd.matches(".*[ㄱ-ㅎㅏ-ㅣ가-힣].*")) {
            throw new UserInvalidValueException("한글은 포함될 수 없습니다.");
        }

        //------------------------------------------------------------------

        return CheckUserPwdResponseDto.builder()
                .message("사용 가능한 비밀번호 입니다.")
                .isValid(true)
                .build();

    }

    public boolean booleanCheckUserPwd(String userPwd) {
        if (userPwd.length() < 6 || userPwd.length() > 18 || !userPwd.matches(".*[a-z].*") || !userPwd.matches(".*[A-Z].*")
                || !userPwd.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*") || userPwd.contains(" ") || containsEmoji(userPwd)) {
            return false;
        } else {
            return true;
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

