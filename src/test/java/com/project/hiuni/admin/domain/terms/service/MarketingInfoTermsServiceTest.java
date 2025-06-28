package com.project.hiuni.admin.domain.terms.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.hiuni.admin.domain.terms.entity.MarketingInfoTerms;
import com.project.hiuni.admin.domain.terms.entity.TermsInfo;
import com.project.hiuni.admin.domain.terms.repository.MarketingInfoTermsRepository;
import java.time.LocalDateTime;
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
		LocalDateTime effectiveDate = LocalDateTime.now();

		//when
		marketingInfoTermsService.create(testContent, version, effectiveDate);

		//then
		MarketingInfoTerms result = marketingInfoTermsRepository.findById(1L)
			.orElseThrow();

		assertThat(result.getId()).isEqualTo(1L);
		assertThat(result.getTermsInfo().getEffectiveDate()).isEqualTo(effectiveDate);
		assertThat(result.getCreatedAt()).isNotNull();
		assertThat(result.getUpdatedAt()).isNotNull();
	}

	@DisplayName("마케팅 동의 내용을 조회할 수 있다.")
	@Test
	void test2() throws Exception {
		//given
		String testContent = "test_content";
		String version = "2";
		LocalDateTime effectiveDate = LocalDateTime.now();

		marketingInfoTermsRepository.save(
			MarketingInfoTerms.of(TermsInfo.of(testContent, version, effectiveDate))
		);

		//when
		var result = marketingInfoTermsService.findByVersion(version);

		//then
		assertThat(result.getContents()).isEqualTo(testContent);
		assertThat(result.getVersion()).isEqualTo(version);
	}
}