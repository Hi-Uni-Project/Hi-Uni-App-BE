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
@Table(name = "personal_info_term")
@Entity
public class PersonalInfoTerms extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private TermsInfo termsInfo;

	@Builder
	private PersonalInfoTerms(Long id, TermsInfo termsInfo) {
		this.id = id;
		this.termsInfo = termsInfo;
	}

	public static PersonalInfoTerms of(TermsInfo termsInfo) {
		return PersonalInfoTerms.builder()
			.termsInfo(termsInfo)
			.build();
	}
}
