package com.project.hiuni.admin.domain.agreement.entity;

import com.project.hiuni.admin.common.BaseEntity;
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
@Table(name = "service_improvement_agreement")
@Entity
public class ServiceImprovementAgreement extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Embedded
	private AgreementInfo agreementInfo;

	@Builder
	private ServiceImprovementAgreement(Long id, AgreementInfo agreementInfo) {
		this.id = id;
		this.agreementInfo = agreementInfo;
	}

	public static ServiceImprovementAgreement from(AgreementInfo agreementInfo) {
		return ServiceImprovementAgreement.builder()
			.agreementInfo(agreementInfo)
			.build();
	}
}
