package com.qoormthon.todool.domain.user.application.port.out;

import com.qoormthon.todool.domain.user.domain.model.User;

public interface FindUserPort {
    User findByUserId(String userId);
}
