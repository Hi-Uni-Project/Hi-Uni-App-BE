package com.project.hiuni.admin.domain.terms.repository;

import com.project.hiuni.admin.domain.terms.entity.ServiceImprovementTerms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceImprovementTermsRepository extends
	JpaRepository<ServiceImprovementTerms, Long> {

}