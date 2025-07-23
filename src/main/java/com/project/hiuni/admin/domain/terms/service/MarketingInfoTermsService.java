package com.project.hiuni.admin.domain.terms.service;

import com.project.hiuni.admin.domain.terms.entity.MarketingInfoTerms;
import com.project.hiuni.admin.domain.terms.entity.TermsInfo;
import com.project.hiuni.admin.domain.terms.repository.MarketingInfoTermsRepository;
import com.project.hiuni.global.exception.ErrorCode;
import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MarketingInfoTermsService {

	private final MarketingInfoTermsRepository marketingInfoTermsRepository;

	public void create(String content, String version, LocalDateTime effectiveDate) {

		MarketingInfoTerms marketingInfoTerms = MarketingInfoTerms.of(
			TermsInfo.of(content, version, effectiveDate)
		);

		marketingInfoTermsRepository.save(marketingInfoTerms);
	}

	public MarketingInfoTerms getByVersion(String version) {
		return marketingInfoTermsRepository.findMarketingInfoTermsByTermsInfo_AgreeVersion(version)
			.orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_FOUND.getMessage() + ":" + version));
	}

	public MarketingInfoTerms getByLatest() {
		return marketingInfoTermsRepository.findTopByOrderByCreatedAtDesc()
			.orElseThrow(() -> new NoSuchElementException(ErrorCode.NOT_FOUND.getMessage()));
	}
}
