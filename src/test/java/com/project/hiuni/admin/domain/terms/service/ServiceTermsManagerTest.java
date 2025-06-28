package com.project.hiuni.admin.domain.terms.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.hiuni.admin.domain.terms.entity.ServiceTerms;
import com.project.hiuni.admin.domain.terms.repository.ServiceTermsRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class ServiceTermsManagerTest {

	@Autowired
	private ServiceTermsManager serviceTermsManager;

	@Autowired
	private ServiceTermsRepository serviceTermsRepository;

	@DisplayName("서비스 이용 약관을 저장할 수 있다.")
	@Test
	void test1() throws Exception {
		//given
		String testContent = "test_content";
		String version = "1";
		LocalDateTime effectiveDate = LocalDateTime.now();

		//when
		serviceTermsManager.create(testContent, version, effectiveDate);

		//then
		ServiceTerms result = serviceTermsRepository.findById(1L)
			.orElseThrow();
		assertThat(result.getId()).isEqualTo(1L);
	}
}