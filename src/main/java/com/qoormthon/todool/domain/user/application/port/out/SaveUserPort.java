package com.qoormthon.todool.domain.user.application.port.out;

import com.qoormthon.todool.domain.user.domain.model.User;

public interface SaveUserPort {
    void saveUser(User user);
}
