package com.project.hiuni.admin.domain.agreement.repository;

import com.project.hiuni.admin.domain.agreement.entity.ServiceTermsAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceTermsAgreementRepository extends
	JpaRepository<ServiceTermsAgreement, Long> {

}