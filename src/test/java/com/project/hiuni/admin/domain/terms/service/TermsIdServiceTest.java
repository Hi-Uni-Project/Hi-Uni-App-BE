package com.project.hiuni.admin.domain.terms.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;

import com.project.hiuni.admin.domain.terms.entity.IdentityVerification;
import com.project.hiuni.admin.domain.terms.entity.MarketingInfoTerms;
import com.project.hiuni.admin.domain.terms.entity.PersonalInfoTerms;
import com.project.hiuni.admin.domain.terms.entity.ServiceImprovementTerms;
import com.project.hiuni.admin.domain.terms.entity.ServiceTerms;
import com.project.hiuni.admin.domain.terms.entity.TermsInfo;
import com.project.hiuni.admin.domain.terms.repository.IdentityVerificationRepository;
import com.project.hiuni.admin.domain.terms.repository.MarketingInfoTermsRepository;
import com.project.hiuni.admin.domain.terms.repository.PersonalInfoTermsRepository;
import com.project.hiuni.admin.domain.terms.repository.ServiceImprovementTermsRepository;
import com.project.hiuni.admin.domain.terms.repository.ServiceTermsRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class TermsIdServiceTest {

	@Autowired
	private TermsIdService termsIdService;
	@Autowired
	private IdentityVerificationRepository identityVerificationRepository;
	@Autowired
	private MarketingInfoTermsRepository marketingInfoTermsRepository;
	@Autowired
	private PersonalInfoTermsRepository personalInfoTermsRepository;
	@Autowired
	private ServiceImprovementTermsRepository serviceImprovementTermsRepository;
	@Autowired
	private ServiceTermsRepository serviceTermsRepository;

	@DisplayName("가장 최근 약관 동의 id를 캐싱할 수 있다.")
	@Test
	void test1() throws Exception {
		//given
		String testContent1 = "test_content";
		String version1 = "1";
		TermsInfo termsInfo1 = TermsInfo.of(testContent1, version1, LocalDateTime.now());

		String testContent2 = "test_content2";
		String version2 = "2";
		TermsInfo termsInfo2 = TermsInfo.of(testContent2, version2, LocalDateTime.now());

		identityVerificationRepository.save(IdentityVerification.of(termsInfo1));
		marketingInfoTermsRepository.save(MarketingInfoTerms.of(termsInfo1));
		personalInfoTermsRepository.save(PersonalInfoTerms.of(termsInfo1));
		serviceImprovementTermsRepository.save(ServiceImprovementTerms.of(termsInfo1));
		serviceTermsRepository.save(ServiceTerms.of(termsInfo1));


		identityVerificationRepository.save(IdentityVerification.of(termsInfo2));
		marketingInfoTermsRepository.save(MarketingInfoTerms.of(termsInfo2));
		personalInfoTermsRepository.save(PersonalInfoTerms.of(termsInfo2));
		serviceImprovementTermsRepository.save(ServiceImprovementTerms.of(termsInfo2));
		serviceTermsRepository.save(ServiceTerms.of(termsInfo2));

		//when
		termsIdService.cacheTermsIds();

		//then
		var requiredTermIds = termsIdService.getRequiredTermIds();
		var optionalTermIds = termsIdService.getOptionalTermIds();

		assertThat(requiredTermIds).hasSize(3)
			.contains(entry("identityTermId", 2L), entry("personalTermId", 2L),  entry("serviceTermId", 2L));

		assertThat(optionalTermIds).hasSize(2)
			.contains(entry("marketingTermId", 2L), entry("serviceImprovementTermId", 2L));
	}

}