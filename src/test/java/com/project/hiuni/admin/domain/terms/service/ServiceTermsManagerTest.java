package com.project.hiuni.admin.domain.terms.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.hiuni.admin.domain.terms.entity.ServiceTerms;
import com.project.hiuni.admin.domain.terms.entity.TermsInfo;
import com.project.hiuni.admin.domain.terms.repository.ServiceTermsRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
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

	@BeforeEach
	void setUp() {
		serviceTermsRepository.deleteAll();
	}

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

	@DisplayName("서비스 이용 약관을 버전에 따라 조회할 수 있다.")
	@Test
	void test2() throws Exception {
		//given
		String testContent = "test_content";
		String version = "2";
		LocalDateTime effectiveDate = LocalDateTime.now();

		serviceTermsRepository.save(
			ServiceTerms.of(TermsInfo.of(testContent, version, effectiveDate))
		);

		//when
		var result = serviceTermsManager.getByVersion(version);

		//then
		assertThat(result.getContents()).isEqualTo(testContent);
		assertThat(result.getVersion()).isEqualTo(version);
	}

	@DisplayName("서비스 이용 약관의 최근 버전을 조회할 수 있다.")
	@Test
	void test3() throws Exception {
		//given
		String testContent1 = "test_content";
		String version1 = "2";
		LocalDateTime effectiveDate1 = LocalDateTime.now();

		String testContent2 = "test_content3";
		String version2 = "3";
		LocalDateTime effectiveDate2 = LocalDateTime.now();

		serviceTermsRepository.save(
			ServiceTerms.of(TermsInfo.of(testContent1, version1, effectiveDate1))
		);
		serviceTermsRepository.save(
			ServiceTerms.of(TermsInfo.of(testContent2, version2, effectiveDate2))
		);

		//when
		var result = serviceTermsManager.getByLastest();

		//then
		assertThat(result.getContents()).isEqualTo(testContent2);
		assertThat(result.getVersion()).isEqualTo(version2);
	}
}