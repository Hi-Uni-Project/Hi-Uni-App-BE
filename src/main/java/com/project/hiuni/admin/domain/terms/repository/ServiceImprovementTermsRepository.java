package com.project.hiuni.admin.domain.terms.repository;

import com.project.hiuni.admin.domain.terms.entity.ServiceImprovementTerms;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceImprovementTermsRepository extends
	JpaRepository<ServiceImprovementTerms, Long> {

	Optional<ServiceImprovementTerms> findIdentityVerificationByTermsInfo_AgreeVersion(String version);
	Optional<ServiceImprovementTerms> findTopByOrderByCreatedAtDesc();
	Long findTopIdByOrderByIdDesc();
}