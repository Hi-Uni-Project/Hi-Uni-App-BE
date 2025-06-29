package com.project.hiuni.admin.domain.agreement.entity;

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
public class AgreementInfo {

	private Long userId;

	private Long termsId;

	private LocalDateTime agreedAt;

	@Builder
	public AgreementInfo(long userId, long termsId, LocalDateTime agreedAt) {
		this.userId = userId;
		this.termsId = termsId;
		this.agreedAt = agreedAt;
	}

	public static AgreementInfo of(long userId, long termsId, LocalDateTime agreedAt) {
		return new AgreementInfo(userId, termsId, agreedAt);
	}
}
