package com.project.hiuni.admin.domain.terms.service;

import com.project.hiuni.admin.domain.terms.repository.IdentityVerificationRepository;
import com.project.hiuni.admin.domain.terms.repository.MarketingInfoTermsRepository;
import com.project.hiuni.admin.domain.terms.repository.PersonalInfoTermsRepository;
import com.project.hiuni.admin.domain.terms.repository.ServiceImprovementTermsRepository;
import com.project.hiuni.admin.domain.terms.repository.ServiceTermsRepository;
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

	private final IdentityVerificationRepository identityVerificationRepository;
	private final MarketingInfoTermsRepository marketingInfoTermsRepository;
	private final PersonalInfoTermsRepository personalInfoTermsRepository;
	private final ServiceImprovementTermsRepository serviceImprovementTermsRepository;
	private final ServiceTermsRepository serviceTermsRepository;


	public void cacheTermsIds(){

		Long identityTermId = identityVerificationRepository.findTopIdByOrderByIdDesc();
		Long marketingTermId = marketingInfoTermsRepository.findTopIdByOrderByIdDesc();
		Long personalTermId = personalInfoTermsRepository.findTopIdByOrderByIdDesc();
		Long serviceImprovementTermId = serviceImprovementTermsRepository.findTopIdByOrderByIdDesc();
		Long serviceTermId = serviceTermsRepository.findTopIdByOrderByIdDesc();

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
