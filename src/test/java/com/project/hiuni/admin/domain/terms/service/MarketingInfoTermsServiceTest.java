package com.project.hiuni.admin.domain.terms.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.hiuni.admin.domain.terms.entity.MarketingInfoTerms;
import com.project.hiuni.admin.domain.terms.entity.ServiceImprovementTerms;
import com.project.hiuni.admin.domain.terms.repository.MarketingInfoTermsRepository;
import com.project.hiuni.admin.domain.terms.repository.ServiceImprovementTermsRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class MarketingInfoTermsServiceTest {

	@Autowired
	private MarketingInfoTermsService marketingInfoTermsService;

	@Autowired
	private MarketingInfoTermsRepository marketingInfoTermsRepository;


	@DisplayName("서비스 개선 동의 내역을 저장할 수 있다.")
	@Test
	void test1() throws Exception {
		//given
		String testContent = "test_content";
		String version = "1";

		//when
		marketingInfoTermsService.create(testContent, version);

		//then
		MarketingInfoTerms result = marketingInfoTermsRepository.findById(1L)
			.orElseThrow();

		assertThat(result.getId()).isEqualTo(1L);
		assertThat(result.getTermsInfo().getEffectiveDate()).isNull();
		assertThat(result.getCreatedAt()).isNotNull();
		assertThat(result.getUpdatedAt()).isNotNull();
	}
}