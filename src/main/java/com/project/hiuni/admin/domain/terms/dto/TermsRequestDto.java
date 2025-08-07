package com.project.hiuni.admin.domain.terms.dto;

import java.time.LocalDateTime;

public record TermsRequestDto(String content, String version, LocalDateTime effectiveDate) {
}
