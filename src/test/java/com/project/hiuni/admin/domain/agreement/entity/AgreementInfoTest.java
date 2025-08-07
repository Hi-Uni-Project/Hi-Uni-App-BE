package com.project.hiuni.admin.domain.agreement.entity;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AgreementInfoTest {


	@DisplayName("AgreementInfo 객체를 생성할 수 있다.")
	@Test
	void test1() throws Exception {
		//given
		long userId = 1L;
		long termsId = 2L;
		LocalDateTime agreedAt = LocalDateTime.now();

		//when
		AgreementInfo result = AgreementInfo.of(userId, termsId, agreedAt);

		//then
		assertThat(result).isEqualTo(new AgreementInfo(userId, termsId, agreedAt));
	}
}