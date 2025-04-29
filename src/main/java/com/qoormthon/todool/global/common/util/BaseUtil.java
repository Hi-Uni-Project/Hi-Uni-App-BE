package com.qoormthon.todool.global.common.util;

import jakarta.servlet.http.HttpServletRequest;
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

    public String getClientIpv4(HttpServletRequest request) {
        String[] headerNames = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_CLIENT_IP",
                "HTTP_X_FORWARDED_FOR"
        };

        String ip = null;
        for (String headerName : headerNames) {
            ip = request.getHeader(headerName);
            if (ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
                // 쉼표로 구분된 경우 첫 번째 IP 사용
                ip = ip.split(",")[0].trim();
                break;
            }
        }
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        if (ip != null && ip.matches("^(?:[0-9]{1,3}\\.){3}[0-9]{1,3}$")) {
            return ip;
        }
        return "127.0.0.1";
    }

}
