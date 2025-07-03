package com.project.hiuni.admin.domain.agreement.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import com.project.hiuni.admin.domain.agreement.repository.IdentityVerificationAgreementRepository;
import com.project.hiuni.admin.domain.agreement.repository.MarketingAgreementRepository;
import com.project.hiuni.admin.domain.agreement.repository.PersonalInfoAgreementRepository;
import com.project.hiuni.admin.domain.agreement.repository.ServiceImprovementAgreementRepository;
import com.project.hiuni.admin.domain.agreement.repository.ServiceTermsAgreementRepository;
import com.project.hiuni.admin.domain.terms.service.TermsIdService;
import com.project.hiuni.domain.user.v1.service.UserAgreementService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserAgreementServiceTest {

	@Autowired
	private UserAgreementService userAgreementService;
	@Autowired
	private ServiceTermsAgreementRepository serviceTermsAgreementRepository;
	@Autowired
	private PersonalInfoAgreementRepository personalInfoAgreementRepository;
	@Autowired
	private IdentityVerificationAgreementRepository identityVerificationAgreementRepository;
	@Autowired
	private MarketingAgreementRepository marketingAgreementRepository;
	@Autowired
	private ServiceImprovementAgreementRepository serviceImprovementAgreementRepository;

	@MockitoBean
	private TermsIdService termsIdService;

	@BeforeEach
	void setUp() {
		serviceTermsAgreementRepository.deleteAll();
		personalInfoAgreementRepository.deleteAll();
		identityVerificationAgreementRepository.deleteAll();
		marketingAgreementRepository.deleteAll();
		serviceImprovementAgreementRepository.deleteAll();
	}


	@Nested
	@DisplayName("약관들의 동의 내역을 저장할 수 있다.")
	class AgreementsTest{

		@DisplayName("마케팅 동의 = true, 서비스 개선 = false")
		@Test
		void test1() throws Exception {
			//given
			when(termsIdService.getServiceTermId()).thenReturn(1L);
			when(termsIdService.getPersonalTermId()).thenReturn(2L);
			when(termsIdService.getIdentityTermId()).thenReturn(3L);
			when(termsIdService.getMarketingTermId()).thenReturn(4L);
			when(termsIdService.getServiceImprovementTermId()).thenReturn(5L);

			//when
			userAgreementService.addAgreements(1L, true, false);

			//then
			assertThat(serviceTermsAgreementRepository.findAll())
				.hasSize(1)
				.extracting("agreementInfo.termsId")
				.containsExactly(1L);

			assertThat(personalInfoAgreementRepository.findAll())
				.hasSize(1)
				.extracting("agreementInfo.termsId")
				.containsExactly(2L);

			assertThat(identityVerificationAgreementRepository.findAll())
				.hasSize(1)
				.extracting("agreementInfo.termsId")
				.containsExactly(3L);

			assertThat(marketingAgreementRepository.findAll())
				.hasSize(1)
				.extracting("agreementInfo.termsId")
				.containsExactly(4L);

			assertThat(serviceImprovementAgreementRepository.findAll()).isEmpty();
		}


		@DisplayName("마케팅 동의 = true, 서비스 개선 = true")
		@Test
		void test2() throws Exception {
			//given
			when(termsIdService.getServiceTermId()).thenReturn(1L);
			when(termsIdService.getPersonalTermId()).thenReturn(2L);
			when(termsIdService.getIdentityTermId()).thenReturn(3L);
			when(termsIdService.getMarketingTermId()).thenReturn(4L);
			when(termsIdService.getServiceImprovementTermId()).thenReturn(5L);

			//when
			userAgreementService.addAgreements(1L, true, true);

			//then
			assertThat(serviceTermsAgreementRepository.findAll())
				.hasSize(1)
				.extracting("agreementInfo.termsId")
				.containsExactly(1L);

			assertThat(personalInfoAgreementRepository.findAll())
				.hasSize(1)
				.extracting("agreementInfo.termsId")
				.containsExactly(2L);

			assertThat(identityVerificationAgreementRepository.findAll())
				.hasSize(1)
				.extracting("agreementInfo.termsId")
				.containsExactly(3L);

			assertThat(marketingAgreementRepository.findAll())
				.hasSize(1)
				.extracting("agreementInfo.termsId")
				.containsExactly(4L);

			assertThat(serviceImprovementAgreementRepository.findAll())
				.hasSize(1)
				.extracting("agreementInfo.termsId")
				.containsExactly(5L);
		}

		@DisplayName("마케팅 동의 = false, 서비스 개선 = false")
		@Test
		void test3() throws Exception {
			//given
			when(termsIdService.getServiceTermId()).thenReturn(1L);
			when(termsIdService.getPersonalTermId()).thenReturn(2L);
			when(termsIdService.getIdentityTermId()).thenReturn(3L);
			when(termsIdService.getMarketingTermId()).thenReturn(4L);
			when(termsIdService.getServiceImprovementTermId()).thenReturn(5L);

			//when
			userAgreementService.addAgreements(1L, false, false);

			//then
			assertThat(serviceTermsAgreementRepository.findAll())
				.hasSize(1)
				.extracting("agreementInfo.termsId")
				.containsExactly(1L);

			assertThat(personalInfoAgreementRepository.findAll())
				.hasSize(1)
				.extracting("agreementInfo.termsId")
				.containsExactly(2L);

			assertThat(identityVerificationAgreementRepository.findAll())
				.hasSize(1)
				.extracting("agreementInfo.termsId")
				.containsExactly(3L);

			assertThat(marketingAgreementRepository.findAll()).isEmpty();

			assertThat(serviceImprovementAgreementRepository.findAll()).isEmpty();
		}

	}


	@DisplayName("마케팅 약관 동의 내역을 저장할 수 있다.")
	@Test
	void test2() throws Exception {
		//given
		when(termsIdService.getMarketingTermId()).thenReturn(5L);

		//when
		userAgreementService.addMarketingTerms(1L, LocalDateTime.now());

		//then
		assertThat(marketingAgreementRepository.findAll())
			.hasSize(1)
			.extracting("agreementInfo.termsId")
			.containsExactly(5L);
	}
}