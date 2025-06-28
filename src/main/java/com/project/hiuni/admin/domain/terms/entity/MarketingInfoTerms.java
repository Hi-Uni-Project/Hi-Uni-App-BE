package com.project.hiuni.admin.domain.terms.entity;


import com.project.hiuni.admin.common.BaseEntity;
import com.project.hiuni.admin.domain.terms.entity.TermsInfo;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "marketing_info_terms")
@Entity
public class MarketingInfoTerms extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private TermsInfo termsInfo;

	@Builder
	private MarketingInfoTerms(Long id, TermsInfo termsInfo) {
		this.id = id;
		this.termsInfo = termsInfo;
	}

	public static MarketingInfoTerms of(TermsInfo termsInfo) {
		return MarketingInfoTerms.builder()
			.termsInfo(termsInfo)
			.build();
	}
}
