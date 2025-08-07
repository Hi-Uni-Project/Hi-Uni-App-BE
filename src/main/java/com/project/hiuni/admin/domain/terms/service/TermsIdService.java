package com.project.hiuni.admin.domain.terms.service;

import static com.project.hiuni.admin.domain.terms.service.Terms.IDENTITY;
import static com.project.hiuni.admin.domain.terms.service.Terms.MARKETING;
import static com.project.hiuni.admin.domain.terms.service.Terms.PERSONAL;
import static com.project.hiuni.admin.domain.terms.service.Terms.SERVICE;
import static com.project.hiuni.admin.domain.terms.service.Terms.SERVICE_IMPROVEMENT;

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
		long marketingTermId = marketingInfoTermsService.getByLatest().getId();
		long personalTermId = personalInfoTermsService.getByLastest().getId();
		long serviceImprovementTermId = serviceImprovementTermsManager.getByLastest().getId();
		long serviceTermId = serviceTermsManager.getByLastest().getId();

		requiredTermIds.put(IDENTITY.name(), identityTermId);
		requiredTermIds.put(PERSONAL.name(), personalTermId);
		requiredTermIds.put(SERVICE.name(), serviceTermId);

		optionalTermIds.put(MARKETING.name(), marketingTermId);
		optionalTermIds.put(SERVICE_IMPROVEMENT.name(), serviceImprovementTermId);
	}

	public Map<String, Long> getRequiredTermIds() {
		return requiredTermIds;
	}

	public Map<String, Long> getOptionalTermIds() {
		return optionalTermIds;
	}

	public Long getIdentityTermId() {
		return requiredTermIds.get(IDENTITY.name());
	}

	public Long getMarketingTermId() {
		return optionalTermIds.get(MARKETING.name());
	}

	public Long getPersonalTermId() {
		return requiredTermIds.get(PERSONAL.name());
	}

	public Long getServiceImprovementTermId() {
		return optionalTermIds.get(SERVICE_IMPROVEMENT.name());
	}

	public Long getServiceTermId() {
		return requiredTermIds.get(SERVICE.name());
	}
}
