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

	private Long personalInfoTermId;

	private LocalDateTime agreedAt;

	@Builder
	public AgreementInfo(long userId, long personalInfoTermId, LocalDateTime agreedAt) {
		this.userId = userId;
		this.personalInfoTermId = personalInfoTermId;
		this.agreedAt = agreedAt;
	}

	public static AgreementInfo of(long userId, long personalInfoTermId, LocalDateTime agreedAt) {
		return new AgreementInfo(userId, personalInfoTermId, agreedAt);
	}
}
