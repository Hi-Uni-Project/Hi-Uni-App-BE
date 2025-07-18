package com.project.hiuni.admin.domain.agreement.repository;

import com.project.hiuni.admin.domain.agreement.entity.IdentityVerificationAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentityVerificationAgreementRepository extends
	JpaRepository<IdentityVerificationAgreement, Long> {

}