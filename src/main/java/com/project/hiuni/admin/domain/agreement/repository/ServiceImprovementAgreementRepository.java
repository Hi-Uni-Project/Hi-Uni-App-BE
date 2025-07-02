package com.project.hiuni.admin.domain.agreement.repository;

import com.project.hiuni.admin.domain.agreement.entity.ServiceImprovementAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceImprovementAgreementRepository extends
	JpaRepository<ServiceImprovementAgreement, Long> {

}