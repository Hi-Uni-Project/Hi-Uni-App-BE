package com.project.hiuni.admin.domain.terms.repository;

import com.project.hiuni.admin.domain.terms.entity.ServiceTerms;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceTermsRepository extends JpaRepository<ServiceTerms, Long> {

}