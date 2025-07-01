package com.project.hiuni.admin.domain.terms.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.hiuni.admin.domain.terms.entity.ServiceImprovementTerms;
import com.project.hiuni.admin.domain.terms.entity.TermsInfo;
import com.project.hiuni.admin.domain.terms.repository.ServiceImprovementTermsRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
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

	@BeforeEach
	void setUp() {
		serviceImprovementTermsRepository.deleteAll();
	}

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

	@DisplayName("서비스 개선 동의 내용을 버전에 따라 조회할 수 있다.")
	@Test
	void test2() throws Exception {
		//given
		String testContent = "test_content";
		String version = "2";
		LocalDateTime effectiveDate = LocalDateTime.now();

		serviceImprovementTermsRepository.save(
			ServiceImprovementTerms.of(TermsInfo.of(testContent, version, effectiveDate))
		);

		//when
		var result = serviceImprovementTermsManager.getByVersion(version);

		//then
		assertThat(result.getContents()).isEqualTo(testContent);
		assertThat(result.getVersion()).isEqualTo(version);
	}

	@DisplayName("서비스 개선 동의 내용의 최근 버전을 조회할 수 있다.")
	@Test
	void test3() throws Exception {
		//given
		String testContent1 = "test_content";
		String version1 = "2";
		LocalDateTime effectiveDate1 = LocalDateTime.now();

		String testContent2 = "test_content3";
		String version2 = "3";
		LocalDateTime effectiveDate2 = LocalDateTime.now();

		serviceImprovementTermsRepository.save(
			ServiceImprovementTerms.of(TermsInfo.of(testContent1, version1, effectiveDate1))
		);
		serviceImprovementTermsRepository.save(
			ServiceImprovementTerms.of(TermsInfo.of(testContent2, version2, effectiveDate2))
		);

		//when
		var result = serviceImprovementTermsManager.getByLastest();

		//then
		assertThat(result.getContents()).isEqualTo(testContent2);
		assertThat(result.getVersion()).isEqualTo(version2);
	}
}