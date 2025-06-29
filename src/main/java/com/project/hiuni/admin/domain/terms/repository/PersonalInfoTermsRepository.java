package com.project.hiuni.admin.domain.terms.repository;

import com.project.hiuni.admin.domain.terms.entity.PersonalInfoTerms;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalInfoTermsRepository extends JpaRepository<PersonalInfoTerms, Long> {

	Optional<PersonalInfoTerms> findIdentityVerificationByTermsInfo_AgreeVersion(String version);
	Optional<PersonalInfoTerms> findTopByOrderByCreatedAtDesc();
	Long findTopIdByOrderByIdDesc();
}