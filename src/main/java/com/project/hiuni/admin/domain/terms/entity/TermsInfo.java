package com.project.hiuni.admin.domain.terms.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.Embeddable;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@Embeddable
public class TermsInfo {

	private String agreeContents;
	private String agreeVersion;
	@Nullable
	private LocalDateTime effectiveDate;

	@Builder
	public TermsInfo(String agreeContents, String agreeVersion, @Nullable LocalDateTime effectiveDate) {
		this.agreeContents = agreeContents;
		this.agreeVersion = agreeVersion;
		this.effectiveDate = effectiveDate;
	}

	public static TermsInfo of(
		String agreeContents,
		String agreeVersion,
		LocalDateTime effectiveDate
	) {
		return new TermsInfo(agreeContents, agreeVersion, effectiveDate);
	}
}
