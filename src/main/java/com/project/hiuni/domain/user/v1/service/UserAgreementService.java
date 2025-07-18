package com.project.hiuni.domain.user.v1.service;

import com.project.hiuni.admin.domain.agreement.entity.AgreementInfo;
import com.project.hiuni.admin.domain.agreement.entity.IdentityVerificationAgreement;
import com.project.hiuni.admin.domain.agreement.entity.MarketingAgreement;
import com.project.hiuni.admin.domain.agreement.entity.PersonalInfoAgreement;
import com.project.hiuni.admin.domain.agreement.entity.ServiceImprovementAgreement;
import com.project.hiuni.admin.domain.agreement.entity.ServiceTermsAgreement;
import com.project.hiuni.admin.domain.agreement.repository.IdentityVerificationAgreementRepository;
import com.project.hiuni.admin.domain.agreement.repository.MarketingAgreementRepository;
import com.project.hiuni.admin.domain.agreement.repository.PersonalInfoAgreementRepository;
import com.project.hiuni.admin.domain.agreement.repository.ServiceImprovementAgreementRepository;
import com.project.hiuni.admin.domain.agreement.repository.ServiceTermsAgreementRepository;
import com.project.hiuni.admin.domain.terms.service.TermsIdService;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class UserAgreementService {

	private final TermsIdService termsIdService;
	private final IdentityVerificationAgreementRepository identityVerificationAgreementRepository;
	private final MarketingAgreementRepository marketingAgreementRepository;
	private final PersonalInfoAgreementRepository personalInfoAgreementRepository;
	private final ServiceTermsAgreementRepository serviceTermsAgreementRepository;
	private final ServiceImprovementAgreementRepository serviceImprovementAgreementRepository;

    public void addAgreements(long userId, boolean isMarketingAgreed, boolean isServiceImprovementAgreed) {

		LocalDateTime agreementDate = LocalDateTime.now();

	    addRequiredTerms(userId, agreementDate);

	    if(isMarketingAgreed) {
		    addMarketingTerms(userId, agreementDate);
	    }

		if(isServiceImprovementAgreed) {
			addServiceImprovementTerms(userId, agreementDate);
		}
    }

	public void addMarketingTerms(long userId, LocalDateTime agreedAt) {
		marketingAgreementRepository.save(MarketingAgreement.from(
			AgreementInfo.of(userId, termsIdService.getMarketingTermId(), agreedAt))
		);
		log.info("marketing agreement :: userId = {}, date = {}", userId, agreedAt);
	}

	private void addRequiredTerms(long userId, LocalDateTime agreedAt) {

		serviceTermsAgreementRepository.save(ServiceTermsAgreement.from(
			AgreementInfo.of(userId, termsIdService.getServiceTermId(), agreedAt))
		);

		personalInfoAgreementRepository.save(PersonalInfoAgreement.from(
			AgreementInfo.of(userId, termsIdService.getPersonalTermId(), agreedAt))
		);

		identityVerificationAgreementRepository.save(IdentityVerificationAgreement.from(
			AgreementInfo.of(userId, termsIdService.getIdentityTermId(), agreedAt))
		);
		log.info("required agreement :: userId = {}, date = {}", userId, agreedAt);
	}


	private void addServiceImprovementTerms(long userId, LocalDateTime agreedAt) {

		serviceImprovementAgreementRepository.save(ServiceImprovementAgreement.from(
			AgreementInfo.of(userId, termsIdService.getServiceImprovementTermId(), agreedAt))
		);
		log.info("service improvement agreement :: userId = {}, date = {}", userId, agreedAt);
	}
}
