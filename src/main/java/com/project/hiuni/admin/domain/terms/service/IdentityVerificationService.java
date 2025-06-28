package com.project.hiuni.admin.domain.terms.service;

import com.project.hiuni.admin.domain.terms.entity.IdentityVerification;
import com.project.hiuni.admin.domain.terms.entity.TermsInfo;
import com.project.hiuni.admin.domain.terms.repository.IdentityVerificationRepository;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class IdentityVerificationService {

	private final IdentityVerificationRepository identityVerificationRepository;

	public void create(String content, String version, LocalDateTime effectiveDate) {

		IdentityVerification identityVerification = IdentityVerification.of(
			TermsInfo.of(content, version,effectiveDate)
		);

		identityVerificationRepository.save(identityVerification);
	}

	public IdentityVerification findByVersion(String version) {

		return identityVerificationRepository.findIdentityVerificationByTermsInfo_AgreeVersion(version)
			.orElseThrow(() -> new NoSuchElementException("no content by " + version));
	}
}
