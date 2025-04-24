package com.qoormthon.todool.global.common.util;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@Setter
@Component
public class BaseUtil {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public LocalDateTime getNow() {
        return LocalDateTime.now();
    }

    public LocalDate getToday() {
        return LocalDate.now();
    }

    public String getTodayAndTime() {
        return getNow().format(formatter);
    }

    public String getCode() {
        SecureRandom random = new SecureRandom();
        int code = 100000 + random.nextInt(900000);
        return String.valueOf(code);
    }

    public String getUUID() {
        return UUID.randomUUID().toString();
    }

}
