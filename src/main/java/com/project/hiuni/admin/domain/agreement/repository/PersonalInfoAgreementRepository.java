package com.project.hiuni.admin.domain.agreement.repository;

import com.project.hiuni.admin.domain.agreement.entity.PersonalInfoAgreement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonalInfoAgreementRepository extends
	JpaRepository<PersonalInfoAgreement, Long> {

}