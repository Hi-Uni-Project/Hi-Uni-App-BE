package com.project.hiuni.admin.domain.terms.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.hiuni.admin.domain.terms.entity.PersonalInfoTerms;
import com.project.hiuni.admin.domain.terms.entity.TermsInfo;
import com.project.hiuni.admin.domain.terms.repository.PersonalInfoTermsRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class PersonalInfoTermsServiceTest {

	@Autowired
	private PersonalInfoTermsService personalInfoTermsService;

	@Autowired
	private PersonalInfoTermsRepository personalInfoTermsRepository;

	@BeforeEach
	void setUp() {
		personalInfoTermsRepository.deleteAll();
	}

	@DisplayName("서비스 개선 동의 내역을 저장할 수 있다.")
	@Test
	void test1() throws Exception {
		//given
		String testContent = "test_content";
		String version = "1";
		LocalDateTime effectiveDate = LocalDateTime.now();

		//when
		personalInfoTermsService.create(testContent, version, effectiveDate);

		//then
		PersonalInfoTerms result = personalInfoTermsRepository.findById(1L)
			.orElseThrow();

		assertThat(result.getId()).isEqualTo(1L);
		assertThat(result.getTermsInfo().getEffectiveDate()).isEqualTo(effectiveDate);
		assertThat(result.getCreatedAt()).isNotNull();
		assertThat(result.getUpdatedAt()).isNotNull();
	}

	@DisplayName("개인 정보 수집 동의 내용을 버전에 따라 조회할 수 있다.")
	@Test
	void test2() throws Exception {
		//given
		String testContent = "test_content";
		String version = "2";
		LocalDateTime effectiveDate = LocalDateTime.now();

		personalInfoTermsRepository.save(
			PersonalInfoTerms.of(TermsInfo.of(testContent, version, effectiveDate))
		);

		//when
		var result = personalInfoTermsService.getByVersion(version);

		//then
		assertThat(result.getContents()).isEqualTo(testContent);
		assertThat(result.getVersion()).isEqualTo(version);
	}

	@DisplayName("개인 정보 수집 동의 내용의 최근 버전을 조회할 수 있다.")
	@Test
	void test3() throws Exception {
		//given
		String testContent1 = "test_content";
		String version1 = "2";
		LocalDateTime effectiveDate1 = LocalDateTime.now();

		String testContent2 = "test_content3";
		String version2 = "3";
		LocalDateTime effectiveDate2 = LocalDateTime.now();

		personalInfoTermsRepository.save(
			PersonalInfoTerms.of(TermsInfo.of(testContent1, version1, effectiveDate1))
		);
		personalInfoTermsRepository.save(
			PersonalInfoTerms.of(TermsInfo.of(testContent2, version2, effectiveDate2))
		);

		//when
		var result = personalInfoTermsService.getByLastest();

		//then
		assertThat(result.getContents()).isEqualTo(testContent2);
		assertThat(result.getVersion()).isEqualTo(version2);
	}
}