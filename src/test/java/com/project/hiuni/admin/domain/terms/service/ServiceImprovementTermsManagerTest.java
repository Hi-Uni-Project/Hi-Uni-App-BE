package com.project.hiuni.admin.domain.terms.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.hiuni.admin.domain.terms.entity.ServiceImprovementTerms;
import com.project.hiuni.admin.domain.terms.repository.ServiceImprovementTermsRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class ServiceImprovementTermsManagerTest {

	@Autowired
	private ServiceImprovementTermsManager serviceImprovementTermsManager;

	@Autowired
	private ServiceImprovementTermsRepository serviceImprovementTermsRepository;


	@DisplayName("서비스 개선 동의 내역을 저장할 수 있다.")
	@Test
	void test1() throws Exception {
		//given
		String testContent = "test_content";
		String version = "1";
		LocalDateTime effectiveDate = LocalDateTime.now();

		//when
		serviceImprovementTermsManager.create(testContent, version, effectiveDate);

		//then
		ServiceImprovementTerms result = serviceImprovementTermsRepository.findById(1L)
			.orElseThrow();

		assertThat(result.getId()).isEqualTo(1L);
		assertThat(result.getTermsInfo().getEffectiveDate()).isEqualTo(effectiveDate);
		assertThat(result.getCreatedAt()).isNotNull();
		assertThat(result.getUpdatedAt()).isNotNull();
	}
}