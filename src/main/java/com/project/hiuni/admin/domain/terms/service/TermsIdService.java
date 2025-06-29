package com.project.hiuni.admin.domain.terms.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class TermsIdService {

	private static final Map<String, Long> requiredTermIds = new ConcurrentHashMap<>();
	private static final Map<String, Long> optionalTermIds = new ConcurrentHashMap<>();

	private final IdentityVerificationService identityVerificationService;
	private final MarketingInfoTermsService marketingInfoTermsService;
	private final PersonalInfoTermsService personalInfoTermsService;
	private final ServiceImprovementTermsManager serviceImprovementTermsManager;
	private final ServiceTermsManager serviceTermsManager;

	public void cacheTermsIds(){

		long identityTermId = identityVerificationService.getByLatest().getId();
		long marketingTermId = marketingInfoTermsService.getByLastest().getId();
		long personalTermId = personalInfoTermsService.getByLastest().getId();
		long serviceImprovementTermId = serviceImprovementTermsManager.getByLastest().getId();
		long serviceTermId = serviceTermsManager.getByLastest().getId();

		requiredTermIds.put("identityTermId", identityTermId);
		requiredTermIds.put("personalTermId", personalTermId);
		requiredTermIds.put("serviceTermId", serviceTermId);

		optionalTermIds.put("marketingTermId", marketingTermId);
		optionalTermIds.put("serviceImprovementTermId", serviceImprovementTermId);
	}

	public Map<String, Long> getRequiredTermIds() {
		return requiredTermIds;
	}

	public Map<String, Long> getOptionalTermIds() {
		return optionalTermIds;
	}
}
