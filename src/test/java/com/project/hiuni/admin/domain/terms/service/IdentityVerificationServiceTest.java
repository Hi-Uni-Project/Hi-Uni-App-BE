package com.project.hiuni.admin.domain.terms.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.hiuni.admin.domain.terms.entity.IdentityVerification;
import com.project.hiuni.admin.domain.terms.entity.TermsInfo;
import com.project.hiuni.admin.domain.terms.repository.IdentityVerificationRepository;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest
class IdentityVerificationServiceTest {

	@Autowired
	private IdentityVerificationService identityVerificationService;
	@Autowired
	private IdentityVerificationRepository identityVerificationRepository;

	@BeforeEach
	void setUpEach(){
		identityVerificationRepository.deleteAll();
	}

	@DisplayName("회원 동의 확인 내역을 저장할 수 있다.")
	@Test
	void test1() throws Exception {
		//given
		String testContent = "test_content";
		String version = "1";
		LocalDateTime effectiveDate = LocalDateTime.now();

		//when
		identityVerificationService.create(testContent, version, effectiveDate);

		//then
		var result = identityVerificationRepository.findById(1L)
			.orElseThrow();
		assertThat(result.getId()).isEqualTo(1L);
	}

	@DisplayName("회원 가입 동의 내용을 버전별로 조회할 수 있다.")
	@Test
	void test2() throws Exception {
		//given
		String testContent = "test_content";
		String version = "2";
		LocalDateTime effectiveDate = LocalDateTime.now();

		identityVerificationRepository.save(IdentityVerification.of(TermsInfo.of(testContent, version, effectiveDate)));

		//when
		var result = identityVerificationService.getByVersion(version);

		//then
		assertThat(result.getContents()).isEqualTo(testContent);
		assertThat(result.getVersion()).isEqualTo(version);
	}

	@DisplayName("회원 가입 동의 내용 조회 시 최신 버전으로 조회할 수 있다.")
	@Test
	void test3() throws Exception {
		//given
		String testContent1 = "test_content1";
		String version1 = "1";
		LocalDateTime effectiveDate1 = LocalDateTime.now();

		String testContent2 = "test_content2";
		String version2 = "2";
		LocalDateTime effectiveDate2 = LocalDateTime.now();

		identityVerificationRepository.save(IdentityVerification.of(TermsInfo.of(testContent1, version1, effectiveDate1)));
		identityVerificationRepository.save(IdentityVerification.of(TermsInfo.of(testContent2, version2, effectiveDate2)));

		//when
		var result = identityVerificationService.getByLatest();

		//then
		assertThat(result.getContents()).isEqualTo(testContent2);
		assertThat(result.getVersion()).isEqualTo(version2);
	}
}