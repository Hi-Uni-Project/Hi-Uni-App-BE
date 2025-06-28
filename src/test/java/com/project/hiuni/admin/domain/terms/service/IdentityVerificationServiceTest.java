package com.project.hiuni.admin.domain.terms.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.project.hiuni.admin.domain.terms.entity.IdentityVerification;
import com.project.hiuni.admin.domain.terms.entity.TermsInfo;
import com.project.hiuni.admin.domain.terms.repository.IdentityVerificationRepository;
import java.time.LocalDateTime;
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
		IdentityVerification result = identityVerificationRepository.findById(1L)
			.orElseThrow();
		assertThat(result.getId()).isEqualTo(1L);
	}
}