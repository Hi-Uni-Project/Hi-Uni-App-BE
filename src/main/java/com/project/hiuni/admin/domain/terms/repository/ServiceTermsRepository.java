package com.project.hiuni.admin.domain.terms.repository;

import com.project.hiuni.admin.domain.terms.entity.ServiceTerms;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceTermsRepository extends JpaRepository<ServiceTerms, Long> {

	Optional<ServiceTerms> findIdentityVerificationByTermsInfo_AgreeVersion(String version);
	Optional<ServiceTerms> findTopByOrderByCreatedAtDesc();
	Long findTopIdByOrderByIdDesc();
}