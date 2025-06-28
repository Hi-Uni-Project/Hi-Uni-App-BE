package com.project.hiuni.admin.domain.terms.service;

import com.project.hiuni.admin.domain.terms.entity.PersonalInfoTerms;
import com.project.hiuni.admin.domain.terms.entity.TermsInfo;
import com.project.hiuni.admin.domain.terms.repository.PersonalInfoTermsRepository;
import java.time.LocalDateTime;
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
}
