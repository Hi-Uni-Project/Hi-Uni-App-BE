package com.qoormthon.todool.domain.user.application.port.out;

import com.qoormthon.todool.global.infra.univcert.dto.response.UnivcertResponseDto;

public interface UnivcertRqPort {
    boolean isValidUnivEmail(String email, String univName);
}
