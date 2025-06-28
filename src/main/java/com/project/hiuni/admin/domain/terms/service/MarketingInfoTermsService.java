package com.project.hiuni.admin.domain.terms.service;

import com.project.hiuni.admin.domain.terms.entity.MarketingInfoTerms;
import com.project.hiuni.admin.domain.terms.entity.TermsInfo;
import com.project.hiuni.admin.domain.terms.repository.MarketingInfoTermsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MarketingInfoTermsService {

	private final MarketingInfoTermsRepository marketingInfoTermsRepository;

	public void create(String content, String version) {

		MarketingInfoTerms marketingInfoTerms = MarketingInfoTerms.of(
			TermsInfo.of(content, version)
		);

		marketingInfoTermsRepository.save(marketingInfoTerms);
	}
}
