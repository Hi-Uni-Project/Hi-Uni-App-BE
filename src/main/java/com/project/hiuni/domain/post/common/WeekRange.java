package com.project.hiuni.domain.post.common;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public final class WeekRange {

    private static final ZoneId ZONE = ZoneId.of("Asia/Seoul");

    private WeekRange() {}

    public static LocalDateTime start() {
        return LocalDate.now(ZONE)
                .with(DayOfWeek.SUNDAY)
                .atStartOfDay();
    }

    public static LocalDateTime end() {
        return LocalDateTime.now(ZONE);
    }
}
