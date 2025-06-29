package com.project.hiuni.admin.domain.terms.repository;

import com.project.hiuni.admin.domain.terms.entity.IdentityVerification;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IdentityVerificationRepository extends JpaRepository<IdentityVerification, Long> {

	Optional<IdentityVerification> findIdentityVerificationByTermsInfo_AgreeVersion(String version);

	Optional<IdentityVerification> findTopByOrderByCreatedAtDesc();
}