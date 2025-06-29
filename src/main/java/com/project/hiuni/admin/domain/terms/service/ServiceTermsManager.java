package com.project.hiuni.admin.domain.terms.service;

import com.project.hiuni.admin.domain.terms.entity.ServiceTerms;
import com.project.hiuni.admin.domain.terms.entity.TermsInfo;
import com.project.hiuni.admin.domain.terms.repository.ServiceTermsRepository;
import com.project.hiuni.global.exception.ErrorCode;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ServiceTermsManager {

	private final ServiceTermsRepository serviceTermsRepository;

	public void create(String content, String version, LocalDateTime effectiveDate) {

		ServiceTerms serviceTerms = ServiceTerms.of(TermsInfo.of(content, version, effectiveDate));

		serviceTermsRepository.save(serviceTerms);
	}

	public ServiceTerms getByVersion(String version) {
		return serviceTermsRepository.findIdentityVerificationByTermsInfo_AgreeVersion(version)
			.orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_FOUND.getMessage() + ":" + version));
	}

	public ServiceTerms getByLastest() {
		return serviceTermsRepository.findTopByOrderByCreatedAtDesc()
			.orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_FOUND.getMessage()));
	}
}
