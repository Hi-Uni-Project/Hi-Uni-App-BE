package com.project.hiuni.admin.domain.terms.service;

import com.project.hiuni.admin.domain.terms.entity.PersonalInfoTerms;
import com.project.hiuni.admin.domain.terms.entity.TermsInfo;
import com.project.hiuni.admin.domain.terms.repository.PersonalInfoTermsRepository;
import com.project.hiuni.global.exception.ErrorCode;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PersonalInfoTermsService {

	private final PersonalInfoTermsRepository personalInfoTermsRepository;

	public void create(String content, String version, LocalDateTime effectiveDate) {

		PersonalInfoTerms personalInfoTerms = PersonalInfoTerms.of(
			TermsInfo.of(content, version, effectiveDate)
		);

		personalInfoTermsRepository.save(personalInfoTerms);
	}

	public PersonalInfoTerms getByVersion(String version) {
		return personalInfoTermsRepository.findIdentityVerificationByTermsInfo_AgreeVersion(version)
			.orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_FOUND.getMessage() + ":" + version));
	}

	public PersonalInfoTerms getByLastest() {
		return personalInfoTermsRepository.findTopByOrderByCreatedAtDesc()
			.orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_FOUND.getMessage()));
	}
}
