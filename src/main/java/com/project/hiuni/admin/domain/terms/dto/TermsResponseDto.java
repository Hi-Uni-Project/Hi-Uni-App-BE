package com.project.hiuni.admin.domain.terms.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record TermsResponseDto(String content, String version, LocalDateTime effectiveDate) {
}
