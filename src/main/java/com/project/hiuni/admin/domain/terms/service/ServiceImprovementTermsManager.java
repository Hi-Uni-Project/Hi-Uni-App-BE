package com.project.hiuni.admin.domain.terms.service;

import com.project.hiuni.admin.domain.terms.entity.ServiceImprovementTerms;
import com.project.hiuni.admin.domain.terms.entity.TermsInfo;
import com.project.hiuni.admin.domain.terms.repository.ServiceImprovementTermsRepository;
import com.project.hiuni.global.exception.ErrorCode;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ServiceImprovementTermsManager {

	private final ServiceImprovementTermsRepository serviceImprovementTermsRepository;

	public void create(String content, String version, LocalDateTime effectiveDate) {

		ServiceImprovementTerms serviceImprovementTerms = ServiceImprovementTerms.of(
			TermsInfo.of(content, version, effectiveDate)
		);

		serviceImprovementTermsRepository.save(serviceImprovementTerms);
	}

	public ServiceImprovementTerms getByVersion(String version) {
		return serviceImprovementTermsRepository.findIdentityVerificationByTermsInfo_AgreeVersion(version)
			.orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_FOUND.getMessage() + ":" + version));
	}

	public ServiceImprovementTerms getByLastest() {
		return serviceImprovementTermsRepository.findTopByOrderByCreatedAtDesc()
			.orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_FOUND.getMessage()));
	}
}
